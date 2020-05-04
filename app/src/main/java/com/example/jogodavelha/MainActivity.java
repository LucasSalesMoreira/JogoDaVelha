package com.example.jogodavelha;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageButton b11, b12, b13;
    private ImageButton b21, b22, b23;
    private ImageButton b31, b32, b33;

    private Button btReload, btNewPlayers;

    private ImageView imageView = null;
    private TextView textView = null;
    private int r;

    private String player1 = null, player2 = null, jogadorDaVez = null;
    private Bitmap img1 = null;
    private Bitmap img2 = null;
    private String status[][] = new String[3][3];

    private boolean controle = true;
    private int gameMode;
    private String vencedor = "";
    boolean controleJogadaBoot = false;
    boolean gameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        referenciar();
        loadNamesAndImages();
        Toast.makeText(getApplicationContext(), player1 + " & " + player2, Toast.LENGTH_SHORT).show();

        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b11.setEnabled(false);
                status[0][0] = jogadorDaVez;
                controlarVez();
                b11.setImageResource(r);
                if (gameMode == 1)
                    controleJogadaBoot = true;
            }
        });

        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b12.setEnabled(false);
                status[0][1] = jogadorDaVez;
                controlarVez();
                b12.setImageResource(r);
                if (gameMode == 1)
                    controleJogadaBoot = true;
            }
        });

        b13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b13.setEnabled(false);
                status[0][2] = jogadorDaVez;
                controlarVez();
                b13.setImageResource(r);
                if (gameMode == 1)
                    controleJogadaBoot = true;
            }
        });

        b21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b21.setEnabled(false);
                status[1][0] = jogadorDaVez;
                controlarVez();
                b21.setImageResource(r);
                if (gameMode == 1)
                    controleJogadaBoot = true;
            }
        });

        b22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b22.setEnabled(false);
                status[1][1] = jogadorDaVez;
                controlarVez();
                b22.setImageResource(r);
                if (gameMode == 1)
                    controleJogadaBoot = true;
            }
        });

        b23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b23.setEnabled(false);
                status[1][2] = jogadorDaVez;
                controlarVez();
                b23.setImageResource(r);
                if (gameMode == 1)
                    controleJogadaBoot = true;
            }
        });

        b31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b31.setEnabled(false);
                status[2][0] = jogadorDaVez;
                controlarVez();
                b31.setImageResource(r);
                if (gameMode == 1)
                    controleJogadaBoot = true;
            }
        });

        b32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b32.setEnabled(false);
                status[2][1] = jogadorDaVez;
                controlarVez();
                b32.setImageResource(r);
                if (gameMode == 1)
                    controleJogadaBoot = true;
            }
        });

        b33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b33.setEnabled(false);
                status[2][2] = jogadorDaVez;
                controlarVez();
                b33.setImageResource(r);
                if (gameMode == 1)
                    controleJogadaBoot = true;
            }
        });

        btReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadGame();
            }
        });

        btNewPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), IndexActivity.class));
                finishAffinity();
            }
        });

        if (gameMode == 1)
            aguardarVezBoot();

        controlarVez();
    }

    private void referenciar() {
        b11 = (ImageButton) findViewById(R.id.imageButton11);
        b12 = (ImageButton) findViewById(R.id.imageButton12);
        b13 = (ImageButton) findViewById(R.id.imageButton13);
        b21 = (ImageButton) findViewById(R.id.imageButton21);
        b22 = (ImageButton) findViewById(R.id.imageButton22);
        b23 = (ImageButton) findViewById(R.id.imageButton23);
        b31 = (ImageButton) findViewById(R.id.imageButton31);
        b32 = (ImageButton) findViewById(R.id.imageButton32);
        b33 = (ImageButton) findViewById(R.id.imageButton33);

        btReload = (Button) findViewById(R.id.reload);
        btNewPlayers = (Button) findViewById(R.id.voltar);
        textView = (TextView) findViewById(R.id.name);
        imageView = (ImageView) findViewById(R.id.foto);
    }

    private void controlarTravaButtons(boolean controle) {
        if (controle) {
            b11.setEnabled(true);
            b12.setEnabled(true);
            b13.setEnabled(true);
            b21.setEnabled(true);
            b22.setEnabled(true);
            b23.setEnabled(true);
            b31.setEnabled(true);
            b32.setEnabled(true);
            b33.setEnabled(true);
        } else {
            b11.setEnabled(false);
            b12.setEnabled(false);
            b13.setEnabled(false);
            b21.setEnabled(false);
            b22.setEnabled(false);
            b23.setEnabled(false);
            b31.setEnabled(false);
            b32.setEnabled(false);
            b33.setEnabled(false);
        }
    }

    private Bitmap construirImagem(String imagemCodificada) {
        Bitmap img = null;
        if (!imagemCodificada.isEmpty()) {
            byte[] imgCodificada = Base64.decode(imagemCodificada, Base64.DEFAULT);
            img = BitmapFactory.decodeByteArray(imgCodificada, 0, imgCodificada.length);
        }
        return img;
    }

    private void loadNamesAndImages() {
        if (getIntent().getStringExtra("boot").equals("N")) {
            player1 = getIntent().getStringExtra("p1");
            player2 = getIntent().getStringExtra("p2");
            img1 = construirImagem(getIntent().getStringExtra("img1"));
            img2 = construirImagem(getIntent().getStringExtra("img2"));
            gameMode = 2;
        } else if (getIntent().getStringExtra("boot").equals("S")) {
            player1 = getIntent().getStringExtra("p1");
            img1 = construirImagem(getIntent().getStringExtra("img1"));
            player2 = "GamerBoot";
            gameMode = 1;
        }
    }

    private void verificarStatusGameOver() {
        if (status[0][0] != null && status[0][1] != null && status[0][2] != null) {
            if (status[0][0].equals(player1) && status[0][1].equals(player1) && status[0][2].equals(player1)) {
                vencedor = player1;
                gameOver = true;
            } else if (status[0][0].equals(player2) && status[0][1].equals(player2) && status[0][2].equals(player2)) {
                vencedor = player2;
                gameOver = true;
            }
        }

        if (status[1][0] != null && status[1][1] != null && status[1][2] != null) {
            if (status[1][0].equals(player1) && status[1][1].equals(player1) && status[1][2].equals(player1)) {
                vencedor = player1;
                gameOver = true;
            } else if (status[1][0].equals(player2) && status[1][1].equals(player2) && status[1][2].equals(player2)) {
                vencedor = player2;
                gameOver = true;
            }
        }

        if (status[2][0] != null && status[2][1] != null && status[2][2] != null) {
            if (status[2][0].equals(player1) && status[2][1].equals(player1) && status[2][2].equals(player1)) {
                vencedor = player1;
                gameOver = true;
            } else if (status[2][0].equals(player2) && status[2][1].equals(player2) && status[2][2].equals(player2)) {
                vencedor = player2;
                gameOver = true;
            }
        }

        if (status[0][0] != null && status[1][0] != null && status[2][0] != null) {
            if (status[0][0].equals(player1) && status[1][0].equals(player1) && status[2][0].equals(player1)) {
                vencedor = player1;
                gameOver = true;
            } else if (status[0][0].equals(player2) && status[1][0].equals(player2) && status[2][0].equals(player2)) {
                vencedor = player2;
                gameOver = true;
            }
        }

        if (status[0][1] != null && status[1][1] != null && status[2][1] != null) {
            if (status[0][1].equals(player1) && status[1][1].equals(player1) && status[2][1].equals(player1)) {
                vencedor = player1;
                gameOver = true;
            } else if (status[0][1].equals(player2) && status[1][1].equals(player2) && status[2][1].equals(player2)) {
                vencedor = player2;
                gameOver = true;
            }
        }

        if (status[0][2] != null && status[1][2] != null && status[2][2] != null) {
            if (status[0][2].equals(player1) && status[1][2].equals(player1) && status[2][2].equals(player1)) {
                vencedor = player1;
                gameOver = true;
            } else if (status[0][2].equals(player2) && status[1][2].equals(player2) && status[2][2].equals(player2)) {
                vencedor = player2;
                gameOver = true;
            }
        }

        if (status[2][0] != null && status[1][1] != null && status[0][2] != null) {
            if (status[2][0].equals(player1) && status[1][1].equals(player1) && status[0][2].equals(player1)) {
                vencedor = player1;
                gameOver = true;
            } else if (status[2][0].equals(player2) && status[1][1].equals(player2) && status[0][2].equals(player2)) {
                vencedor = player2;
                gameOver = true;
            }
        }

        if (status[0][0] != null && status[1][1] != null && status[2][2] != null) {
            if (status[0][0].equals(player1) && status[1][1].equals(player1) && status[2][2].equals(player1)) {
                vencedor = player1;
                gameOver = true;
            } else if (status[0][0].equals(player2) && status[1][1].equals(player2) && status[2][2].equals(player2)) {
                vencedor = player2;
                gameOver = true;
            }
        }
    }

    private int cont = 0;

    private void reloadGame() {
        for (int l = 0; l < status.length; l++) {
            for (int c = 0; c < status.length; c++) {
                status[l][c] = null;
            }
        }
        b11.setImageResource(R.drawable.editar_simbolo_pequeno);
        b12.setImageResource(R.drawable.editar_simbolo_pequeno);
        b13.setImageResource(R.drawable.editar_simbolo_pequeno);
        b21.setImageResource(R.drawable.editar_simbolo_pequeno);
        b22.setImageResource(R.drawable.editar_simbolo_pequeno);
        b23.setImageResource(R.drawable.editar_simbolo_pequeno);
        b31.setImageResource(R.drawable.editar_simbolo_pequeno);
        b32.setImageResource(R.drawable.editar_simbolo_pequeno);
        b33.setImageResource(R.drawable.editar_simbolo_pequeno);
        cont = 0;
        controle = true;
        jogadorDaVez = player1;
        vencedor = null;
        gameOver = false;
        controlarTravaButtons(true);
        controlarVez();
    }

    private void controlarVez() {
        cont++;
        verificarStatusGameOver();
        if (!gameOver) {
            if (cont > 9) {
                Toast.makeText(getApplicationContext(), "Deu velha", Toast.LENGTH_SHORT).show();
                textView.setText("Deu velha!");
                controlarTravaButtons(false);
            } else {
                if (gameMode == 2) {
                    if (controle) {
                        r = R.drawable.circulo_pegueno;
                        imageView.setImageBitmap(img1);
                        textView.setText("Vez de " + player1);
                        jogadorDaVez = player1;
                        controle = false;
                    } else {
                        r = R.drawable.remover_pequeno;
                        imageView.setImageBitmap(img2);
                        textView.setText("Vez de " + player2);
                        jogadorDaVez = player2;
                        controle = true;
                    }
                } else if (gameMode == 1) {
                    r = R.drawable.remover_pequeno;
                    imageView.setImageBitmap(img1);
                    textView.setText("Vez de " + player1);
                    jogadorDaVez = player1;
                }
            }
        } else {

            if (vencedor.equals(player1))
                r = R.drawable.remover_pequeno;
            else
                r = R.drawable.circulo_pegueno;

            textView.setText(vencedor + " ganhou o jogo!");
            Toast.makeText(getApplicationContext(), vencedor + " ganhou o jogo!", Toast.LENGTH_SHORT).show();
            controlarTravaButtons(false);
        }
    }

    private void aguardarVezBoot() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (controleJogadaBoot && !gameOver) {
                    tratarRespostaBoot();
                    controleJogadaBoot = false;
                }
                aguardarVezBoot();
            }
        };
        new Handler().postDelayed(runnable, 100);
    }

    private void tratarRespostaBoot() {
        int respostaBoot[] = new Boot(status).jogar();
        int linha = respostaBoot[0];
        int coluna = respostaBoot[1];
        int r = R.drawable.circulo_pegueno;
        jogadorDaVez = player2;

        if (linha == 0 && coluna == 0) {

            b11.setEnabled(false);
            b11.setImageResource(r);

        } else if (linha == 0 && coluna == 1) {

            b12.setEnabled(false);
            b12.setImageResource(r);

        } else if (linha == 0 && coluna == 2) {

            b13.setEnabled(false);
            b13.setImageResource(r);

        } else if (linha == 1 && coluna == 0) {

            b21.setEnabled(false);
            b21.setImageResource(r);

        } else if (linha == 1 && coluna == 1) {

            b22.setEnabled(false);
            b22.setImageResource(r);

        } else if (linha == 1 && coluna == 2) {

            b23.setEnabled(false);
            b23.setImageResource(r);

        } else if (linha == 2 && coluna == 0) {

            b31.setEnabled(false);
            b31.setImageResource(r);

        } else if (linha == 2 && coluna == 1) {

            b32.setEnabled(false);
            b32.setImageResource(r);

        } else if (linha == 2 && coluna == 2) {

            b33.setEnabled(false);
            b33.setImageResource(r);

        }

        status[linha][coluna] = jogadorDaVez;
        imageView.setImageBitmap(img1);
        textView.setText("Vez de " + player1);
    }

}
