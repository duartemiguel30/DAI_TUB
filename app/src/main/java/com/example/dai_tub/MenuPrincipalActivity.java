package com.example.dai_tub;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.dai_tub.PerfilActivity;

import java.util.List;

public class MenuPrincipalActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuprincipal);

        // Inicialização do DrawerLayout
        drawerLayout = findViewById(R.id.drawerLayout);

        // Configuração do clique no texto do menu para abrir/fechar o drawer
        TextView menuText = findViewById(R.id.menuText);
        menuText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDrawer();
            }
        });

        // Configuração do botão "Home"
        Button homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ação do botão "Home" (pode ser adicionada posteriormente)
            }
        });

        // Configuração do botão "Perfil"
        Button profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir o layout do perfil
                startActivity(new Intent(MenuPrincipalActivity.this, PerfilActivity.class));
            }
        });

        // Configuração do botão "Ver Rotas"
        Button routesButton = findViewById(R.id.routesButton);
        routesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir o PDF das rotas (se existir)
                openPDF();
            }
        });

        // Adicione outras configurações de botão, se necessário...
    }

    // Método para alternar a abertura/fechamento do drawer
    private void toggleDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    // Método para abrir o PDF
    private void openPDF() {
        // Abrir o arquivo PDF usando um visualizador de PDF embutido
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.rotas);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Verifica se há um visualizador de PDF instalado
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isIntentSafe = activities.size() > 0;

        if (isIntentSafe) {
            try {
                Log.d("OpenPDF", "Abrindo PDF...");
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                // Exibir uma mensagem de erro ou oferecer ao usuário a opção de instalar um visualizador de PDF
            }
        } else {
            // Se nenhum visualizador de PDF estiver instalado, exibir uma mensagem ou oferecer ao usuário a opção de
            // instalar um visualizador de PDF
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Visualizador de PDF não encontrado");
            builder.setMessage("Para visualizar este PDF, é necessário instalar um visualizador de PDF. Deseja instalar um agora?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Abre a Play Store para instalar um visualizador de PDF
                    Intent playStoreIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.adobe.reader"));
                    startActivity(playStoreIntent);
                }
            });
            builder.setNegativeButton("Não", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    // Adicione outras ações de botão, se necessário...
}
