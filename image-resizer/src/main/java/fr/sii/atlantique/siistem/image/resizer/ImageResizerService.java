package fr.sii.atlantique.siistem.image.resizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ImageResizerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageResizerService.class);

    @Value("#{'${image.sizes:100,500}'.split(',')}")
    private List<Integer> imageSizes;

    @RabbitListener
    public void receiveMessage(final String imagePath) {
        LOGGER.info("Resize image : {}", imagePath);
        try {
            BufferedImage originalImage = ImageIO.read(new File(imagePath));
            imageSizes.parallelStream().forEach(imageSize -> {
                try {
                    resizeAndSaveImage(imagePath, originalImage, imageSize);
                } catch (Exception e) {
                    LOGGER.error("Error while resizing image {}, size {}", imagePath, imageSize, e);
                }
            });
        } catch (Exception e) {
            LOGGER.error("Error while resizing image {}", imagePath, e);
        }
    }

    private void resizeAndSaveImage(String imagePath, BufferedImage originalImage, int size) throws IOException {
        LOGGER.info("Resize {} : {}", size, imagePath);
        Dimension dimension = getScaledDimension(new Dimension(originalImage.getWidth(), originalImage.getHeight()), new Dimension(size, size));
        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
        BufferedImage resizeImageHintPng = resizeImage(originalImage, type, dimension);
        ImageIO.write(resizeImageHintPng, "png", new File(imagePath.replace("original", size + "." + size)));
    }

    private Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;

        if (original_width > bound_width) {
            new_width = bound_width;
            new_height = (new_width * original_height) / original_width;
        }

        if (new_height > bound_height) {
            new_height = bound_height;
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int type, Dimension dimension){

        int width = (int) dimension.getWidth();
        int height = (int) dimension.getHeight();

        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;
    }
}
