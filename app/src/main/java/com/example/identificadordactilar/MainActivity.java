package com.example.identificadordactilar;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Executor executor = ContextCompat.getMainExecutor(this);

        biometricPrompt = new BiometricPrompt(
                this,
                executor,
                new BiometricPrompt.AuthenticationCallback() {

                    @Override
                    public void onAuthenticationSucceeded(
                            BiometricPrompt.AuthenticationResult result) {

                        super.onAuthenticationSucceeded(result);

                        Toast.makeText(
                                MainActivity.this,
                                "Autenticación exitosa",
                                Toast.LENGTH_SHORT
                        ).show();

                        Intent intent =
                                new Intent(MainActivity.this, MainActivity2.class);

                        startActivity(intent);
                    }

                    @Override
                    public void onAuthenticationFailed() {

                        super.onAuthenticationFailed();

                        Toast.makeText(
                                MainActivity.this,
                                "Escaneo fallido, huella no reconocida",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    @Override
                    public void onAuthenticationError(
                            int errorCode,
                            CharSequence errString) {

                        super.onAuthenticationError(errorCode, errString);

                        Toast.makeText(
                                MainActivity.this,
                                "Error: " + errString,
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );

        promptInfo =
                new BiometricPrompt.PromptInfo.Builder()
                        .setTitle("BioAccess")
                        .setSubtitle("Autenticación biométrica")
                        .setDescription("Coloca tu huella para iniciar sesión")
                        .setNegativeButtonText("Cancelar")
                        .build();
        Button btnScan = findViewById(R.id.btnScan);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                biometricPrompt.authenticate(promptInfo);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}