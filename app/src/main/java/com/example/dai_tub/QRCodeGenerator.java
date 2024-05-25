package com.example.dai_tub;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class QRCodeGenerator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_generator);

        Bilhete bilhete = getIntent().getParcelableExtra("bilhete");

        Bitmap qrCodeBitmap = gerarCodigoQR(bilhete);

        ImageView qrCodeImageView = findViewById(R.id.qrCodeImageView);
        qrCodeImageView.setImageBitmap(qrCodeBitmap);
    }

    private Bitmap gerarCodigoQR(Bilhete bilhete) {
        String bilheteData = "Nome do Usuário: " + bilhete.getNomeUsuario() + "\n"
                + "Data: " + bilhete.getDataCompra() + "\n"
                + "Validade: " + bilhete.getValidade() + "\n"
                + "Ponto de Partida: " + bilhete.getPontoPartida() + "\n"
                + "Ponto de Chegada: " + bilhete.getPontoChegada();

        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(bilheteData, BarcodeFormat.QR_CODE, 500, 500);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }

        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }}