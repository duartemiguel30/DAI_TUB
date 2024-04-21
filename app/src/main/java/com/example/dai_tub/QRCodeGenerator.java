// QRCodeGenerator.java
package com.example.dai_tub;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class QRCodeGenerator {
    private static final String TAG = QRCodeGenerator.class.getSimpleName();

    public static Bitmap generateQRCode(String data) {
        int width = 4000; // Defina o tamanho máximo permitido aqui
        int height = 4000; // Defina o tamanho máximo permitido aqui
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height);
            int qrCodeWidth = bitMatrix.getWidth();
            int qrCodeHeight = bitMatrix.getHeight();
            int[] pixels = new int[qrCodeWidth * qrCodeHeight];
            for (int y = 0; y < qrCodeHeight; y++) {
                int offset = y * qrCodeWidth;
                for (int x = 0; x < qrCodeWidth; x++) {
                    pixels[offset + x] = bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF;
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(qrCodeWidth, qrCodeHeight, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, qrCodeWidth, 0, 0, qrCodeWidth, qrCodeHeight);
            return bitmap;
        } catch (WriterException e) {
            Log.e(TAG, "Error generating QR code: " + e.getMessage());
            return null;
        }
    }
}
