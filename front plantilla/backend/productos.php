<?php
$servername = "localhost";
$username = "root";
$password = "root"; // Cambia esto si tienes una contraseña
$dbname = "Proyecto"; // Reemplaza con el nombre de tu base de datos

// Crear la conexión
$conn = new mysqli($servername, $username, $password, $dbname);

// Verificar la conexión
if ($conn->connect_error) {
  die("Conexión fallida: " . $conn->connect_error);
}

// Obtener los productos de la tabla bproducto
$sql = "SELECT id, nombre, precio FROM Producto";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
  // Guardar los productos en un array
  $productos = [];
  while($row = $result->fetch_assoc()) {
    $productos[] = $row;
  }
} else {
  $productos = [];
}

$conn->close();
?>