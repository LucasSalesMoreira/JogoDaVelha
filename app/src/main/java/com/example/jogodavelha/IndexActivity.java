package com.example.jogodavelha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class IndexActivity extends AppCompatActivity {

    private String img1 = null, img2 = null;
    private ImageButton btFoto1 = null, btFoto2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        final EditText editText1 = (EditText) findViewById(R.id.jogador1);
        final EditText editText2 = (EditText) findViewById(R.id.jogador2);

        ImageButton btPlay = (ImageButton) findViewById(R.id.play);
        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String player1 = editText1.getText().toString();
                String player2 = editText2.getText().toString();
                if (!player1.equals("") && !player2.equals("") && img1 != null && img2 != null) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class)
                            .putExtra("p1", player1).putExtra("p2", player2)
                            .putExtra("img1", img1).putExtra("img2", img2)
                            .putExtra("boot", "N");
                    startActivity(intent);
                    finishAffinity();
                } else {
                    Toast.makeText(getApplicationContext(), "Você precisa preencher os campos!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageButton btPlaySolo = (ImageButton) findViewById(R.id.play_solo);
        btPlaySolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String player1 = editText1.getText().toString();
                if (!player1.equals("") && img1 != null) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class)
                            .putExtra("p1", player1).putExtra("img1", img1)
                            .putExtra("boot", "S");
                    startActivity(intent);
                    finishAffinity();
                } else {
                    Toast.makeText(getApplicationContext(), "Preencher o player1 e sua imagem!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btFoto1 = (ImageButton) findViewById(R.id.bt_foto_1);
        btFoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto(1);
            }
        });

        btFoto2 = (ImageButton) findViewById(R.id.bt_foto_2);
        btFoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto(2);
            }
        });
    }

    private void takePhoto(int code) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, code);
    }

    private String destruirImagem(Bitmap imagem) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imagem.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String img = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

        return img;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                if (requestCode == 1) {
                    Bitmap img1 = (Bitmap) bundle.get("data");
                    btFoto1.setImageBitmap(img1);
                    this.img1 = destruirImagem(img1);
                } else if (requestCode == 2) {
                    Bitmap img2 = (Bitmap) bundle.get("data");
                    btFoto2.setImageBitmap(img2);
                    this.img2 = destruirImagem(img2);
                }
            }
        }
    }

}
