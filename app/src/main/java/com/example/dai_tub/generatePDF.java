package com.example.dai_tub;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.itextpdf.text.Paragraph;

public class generatePDF {

    public static void generatePDF(Context context, Bilhete bilhete, Bitmap qrCodeBitmap) {
        // Crie um novo documento PDF
        Document document = new Document(PageSize.A4);

        // Defina o nome do arquivo PDF
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "bilhete_" + timeStamp + ".pdf";

        // Verifique se o armazenamento externo está disponível e gravável
        if (isExternalStorageWritable()) {
            // Obtenha o diretório de documentos do armazenamento externo
            File directory = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            if (directory != null) {
                // Crie o arquivo PDF no diretório de documentos
                File pdfFile = new File(directory, fileName);
                try {
                    // Crie um objeto PdfWriter para escrever no documento PDF
                    PdfWriter.getInstance(document, new FileOutputStream(pdfFile));

                    // Abra o documento para edição
                    document.open();

                    // Adicione as informações do bilhete ao PDF
                    document.add(Image.getInstance(bitmapToByteArray(qrCodeBitmap)));
                    document.add(new Paragraph("Nome do Usuário: " + bilhete.getNomeUsuario()));
                    document.add(new Paragraph("Data de Compra: " + bilhete.getDataCompra()));
                    document.add(new Paragraph("Validade: " + bilhete.getValidade()));
                    document.add(new Paragraph("Ponto de Partida: " + bilhete.getPontoPartida()));
                    document.add(new Paragraph("Ponto de Chegada: " + bilhete.getPontoChegada()));

                    // Feche o documento após a edição
                    document.close();

                    // Notifique o usuário que o PDF foi salvo com sucesso
                    Toast.makeText(context, "PDF salvo em " + pdfFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
                } catch (DocumentException | IOException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Erro ao gerar o PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(context, "Armazenamento externo não está disponível para gravação", Toast.LENGTH_SHORT).show();
        }
    }

    // Verifique se o armazenamento externo está disponível e gravável
    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    // Converta um bitmap em um array de bytes
    private static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
