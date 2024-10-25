<?php include 'conexion.php'; ?>  <!-- Incluimos el archivo de conexión -->

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>HandCraftVerse</title>
    <link rel="stylesheet" href="css/style.css" />
  </head>
  <body>
    <nav class="navbar">
      <h1 class="navbar-brand"><a href="index.php">HandCraftVerse</a></h1>
    </nav>

    <div class="container">
      <div class="previewCartegoria">
        <h2 class="tituloEnPag">
          <span>
            <a href="verCategoria.html" class="textoTitulo">Categoria</a>
          </span>
        </h2>

        <div class="carousel-container">
          <button class="carousel-button left" aria-label="Desplazar a la izquierda">&#10094;</button>
          
          <div class="imgFlex">
            <?php
              // Mostramos las tarjetas dinámicamente
              foreach ($productos as $producto) {
                echo '<div class="card">
                        <img alt="" src="img/unnamed.png" />
                        <div class="texto">
                          <p class="nombre">' . $producto['nombre'] . '</p>
                          <p class="precio">' . $producto['precio'] . '€</p>
                        </div>
                      </div>';
              }
            ?>
          </div>

          <button class="carousel-button right" aria-label="Desplazar a la derecha">&#10095;</button>
        </div>
      </div>
    </div>
  </body>
</html>
