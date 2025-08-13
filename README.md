# 💱 Conversor de Monedas - Java API Client

Este proyecto es un **conversor de monedas** desarrollado en Java, el cual utiliza la API de [Exchangerate](https://www.exchangerate-api.com/) para obtener tasas de cambio en tiempo real.  
Su propósito es mostrar cómo consumir APIs externas, manejar datos en formato JSON usando la librería **GSON**, y aplicar buenas prácticas de programación orientada a objetos.

---

## 🚀 Funcionalidades principales
- Conversión de monedas en tiempo real
- Uso de archivo de configuración externo (`config.properties`)
- Manejo de excepciones personalizadas
- Estructura modular para fácil mantenimiento

---

## 🌐 Obtener tu API Key
1. Ingresá a [Exchangerate](https://www.exchangerate-api.com/)
2. Hacé click en **"Get Free API Key"**
3. Registrate con tu correo electrónico
4. Una vez logueado, obtendrás tu clave (API Key) en el panel de usuario

---

## ⚙️ Instalación y configuración

### 1️⃣ Clonar el repositorio
```bash
git clone https://github.com/Julianj09/alura-cursos-oracle-conversor-monedas.git
cd conversor_de_monedas
```

### 2️⃣ Configurar la API Key
Crear un archivo llamado `config.properties` en la carpeta `src` con el siguiente contenido:
```properties
API_KEY=TU_CLAVE_AQUI
```

### 3️⃣ Instalar la librería GSON
- Descargar el archivo `.jar` desde: [GSON - Maven Repository](https://mvnrepository.com/artifact/com.google.code.gson/gson)  
  *(Version Usada: Gson » 2.13.1)*
- En IntelliJ IDEA:
    - Ir a **File > Project Structure > Modules > Dependencies**
    - Presionar el botón **`+`** y seleccionar **"JARs or directories"**
    - Seleccionar el archivo `.jar` descargado
    - Aplicar los cambios

---

## ▶️ Cómo ejecutar el programa
1. Abrir el proyecto en **IntelliJ IDEA** (o tu IDE preferido)
2. Verificar que el archivo `config.properties` esté en el **classpath**
3. Ejecutar la clase principal `ConversorApp`

---

## 📚 Tecnologías utilizadas
- Java 17+
- Librería **GSON**
- API **Exchangerate**

---

## 📜 Licencia
Este proyecto está bajo la licencia **MIT**.  
Podés usarlo, modificarlo y distribuirlo libremente, siempre que se mantenga el aviso de licencia original.

---

✉️ **Contacto:**  
Si querés proponer mejoras o reportar errores, podés crear un *issue* en el repositorio o enviarme un mensaje.
