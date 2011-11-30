package net.sf.xfresh.catering.util;


import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static java.awt.Image.SCALE_SMOOTH;

/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 11/18/11
 * Time: 3:21 PM
 *
 * @author Anton Ohitin
 */


public class ImgUtils {
    public static String get(String url, int id) throws IOException, MalformedURLException {
        String[] splitted = url.split(".");
        String ext = splitted[splitted.length - 1].toLowerCase();
        java.io.BufferedInputStream in = new java.io.BufferedInputStream(new URL(url).openStream());
        java.io.FileOutputStream fos = new java.io.FileOutputStream(
                Consts.MediaPath + ((Integer) id).toString() + "." + ext);
        java.io.BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
        byte[] data = new byte[1024];
        int x = 0;
        while ((x = in.read(data, 0, 1024)) >= 0) {
            bout.write(data, 0, x);
        }
        bout.close();
        in.close();
        makeThumb(id, ext);
        return ((Integer) id).toString() + "." + ext;
    }

    public static BufferedImage getThumbnail(BufferedImage image,
                                             int maxThumbWidth,
                                             int maxThumbHeight
    ) {
        BufferedImage thumbnail = null;
        if (image != null) {
            AffineTransform tx = new AffineTransform();
            // Determine scale so image is not larger than the max height and/or width.
            double scale = scaleToFit(image.getWidth(),
                    image.getHeight(),
                    maxThumbWidth, maxThumbHeight);

            tx.scale(scale, scale);

            double d1 = (double) image.getWidth() * scale;
            double d2 = (double) image.getHeight() * scale;
            thumbnail = new BufferedImage(
                    ((int) d1) < 1 ? 1 : (int) d1,  // don't allow width to be less than 1
                    ((int) d2) < 1 ? 1 : (int) d2,  // don't allow height to be less than 1
                    image.getType() == BufferedImage.TYPE_CUSTOM ?
                            BufferedImage.TYPE_INT_RGB : image.getType());
            Graphics2D g2d = thumbnail.createGraphics();
            g2d.drawImage(image, tx, null);
            g2d.dispose();
        }
        return thumbnail;
    }


    private static double scaleToFit(double w1, double h1, double w2, double h2) {
        double scale = 1.0D;
        if (w1 > h1) {
            if (w1 > w2)
                scale = w2 / w1;
            h1 *= scale;
            if (h1 > h2)
                scale *= h2 / h1;
        } else {
            if (h1 > h2)
                scale = h2 / h1;
            w1 *= scale;
            if (w1 > w2)
                scale *= w2 / w1;
        }
        return scale;
    }

    public static void makeThumb(int id, String ext) throws IOException {
        BufferedImage img = ImageIO.read(new File(Consts.MediaPath + ((Integer) id).toString() + "." + ext));

        ImageIO.write(getThumbnail(img, 150, 150), "jpg", new File(Consts.MediaPath + "thumb/" + ((Integer) id).toString() + ".jpg"));
    }
}
