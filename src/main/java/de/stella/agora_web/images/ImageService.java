package de.stella.agora_web.images;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Time;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import de.stella.agora_web.config.StorageProperties;
import de.stella.agora_web.profiles.exceptions.ProfileNotFoundException;
import de.stella.agora_web.profiles.model.Profile;
import de.stella.agora_web.profiles.repository.ProfileRepository;


@Service
public class ImageService implements IStorageService {

    ImageRepository imageRepository;
    ProfileRepository profileRepository;
    Time time;
    private final Path rootLocation;

    public ImageService(ImageRepository imageRepository, ProfileRepository profileRepository, Time time,
            StorageProperties properties) {
        if (properties.getLocation().trim().length() == 0) {
            throw new StorageException("File upload location can not be Empty.");
        }

        this.rootLocation = Paths.get(properties.getLocation());
        this.imageRepository = imageRepository;
        this.profileRepository = profileRepository;
        this.time = time;
    }

    @Override
    public void saveMainImage(@NonNull Long profileId, MultipartFile file) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ProfileNotFoundException("Product not found"));

        Image searchedMainImage = profile.getImages().stream()
                .filter(Image::isMainImage)
                .findFirst()
                .orElseGet(() -> saveNewMainImage(profile, file));

        if (file != null && searchedMainImage != null) {
            searchedMainImage.setMainImage(true);
            imageRepository.save(searchedMainImage);
        }
    }

    private Image saveNewMainImage(Profile profile, MultipartFile file) {
        String uniqueName = createUniqueName(file);
        Path path2 = load(uniqueName);

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, path2, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeErrorException(null, "File" + uniqueName + "has not been saved");
        }

        return imageRepository.save(
                new Image());
    }

    @Override
    public void saveImages(@NonNull Long productId, MultipartFile[] files) {
        Profile profile = profileRepository.findById(productId)
                .orElseThrow(() -> new ProfileNotFoundException("Product not found"));

        if (files != null) {
            int imageCount = profile.getImages().size();

            if (imageCount + files.length > 10) {
                throw new StorageException("The maximum number of files for one product is exceeded");
            }

            List<Image> newImages = new ArrayList<>();

            for (MultipartFile file : files) {
                String uniqueName = createUniqueName(file);
                Path path2 = load(uniqueName);

                try (InputStream inputStream = file.getInputStream()) {
                    if (file.isEmpty()) {
                        throw new StorageException("Failed to store empty file.");
                    }
                    Files.copy(inputStream, path2, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new RuntimeErrorException(null, "File" + uniqueName + "has not been saved");
                }

                Image newImage = Image.builder()
                        .imageName(uniqueName)
                        .isMainImage(false)
                        .profile(profile)
                        .build();
                newImages.add(newImage);
            }

            imageRepository.saveAll(newImages);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public boolean delete(String filename) {
        try {
            Image image = imageRepository.findByImageName(filename)
                    .orElseThrow(() -> new StorageFileNotFoundException("Image not found in the database"));
            imageRepository.delete(image);
            Path file = rootLocation.resolve(filename);
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public String createUniqueName(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String baseName = fileName.substring(0, fileName.lastIndexOf("."));
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        String combinedName = MessageFormat.format("{0}-{1}.{2}", baseName, time.checkCurrentTime(), fileExtension);

        return combinedName;
    }
}