# TP1 Aplicaciones Móviles - "Pet Care Tracker"

**Integrante:** Paloma Gareis Borgiani 

**Informe del proyecto:** "Pet Care Tracker" es una aplicación mobile diseñada para la gestión y seguimiento de la salud y rutina diaria de una mascota. Surge de la necesidad de centralizar y gestionar la información esencial de una mascota en una plataforma móvil. El objetivo principal es centralizar la información principal del animal (edad, peso, actividades) en una interfaz intuitiva que garantice la **persistencia de los datos** y en donde se pueda hacer un seguimiento del mismo, colocando información sobre el día a día de la mascota.

Para el desarrollo de la interfaz se trabajó bajo un **concepto minimalista y orgánico**. La elección de la paleta de colores, como tonos verdes suaves y crema, busca transmitir una sensación de bienestar y cuidado animal.

## Funcional de las pantallas:

### Pantalla de bienvenida: Splash Screen
      
Se implementó una **Splash Screen** como punto de entrada a la aplicación para que al iniciarla se pueda visualizar el nombre y logo de la app, teniendo una transición que dura unos segundos para luego pasar a la pantalla principal, generando una mejor experiencia UX.

<img width="300" alt="Pet Care Tracker (1)" src="https://github.com/user-attachments/assets/de03c867-ec19-4d15-afec-c8e0fcba002a" />

### Pantalla de Login:
    
Se diseñó el prototipo de acceso para una próxima etapa de desarrollo de la app. Permitir la **autenticación segura** mediante email y contraseña, pudiendo vincular los datos de la o las mascotas a una cuenta personal para tenerlos cargados en la cuenta propia.

<img width="300" alt="Pet Care Tracker (5)" src="https://github.com/user-attachments/assets/f7c0ceae-b9b4-4a6b-8f39-07bdeb004aa3" />

### Pantalla Principal: Dashboard (PANTALLA SELECCIONADA)
      
Es el núcleo de la aplicación, donde se presenta el perfil de la mascota y se gestionan sus datos. 

* **Perfil informativo:** muestra de forma estática los datos de identificación.
* **Datos dinámicos:** presenta la edad y el peso actual, permitiendo un monitoreo rápido de la salud de la mascota.
* **Acciones rápidas:** posee botones interactivos para registrar eventos cotidianos (alimentación, higiene, paseos, etc) de forma instantánea.
* **Acción de edición:** a través de un botón de edición, se despliega una interfaz que permite al usuario actualizar los valores numéricos. Esta acción es de gran importancia para mostrar el crecimiento y cambios físicos de la mascota en tiempo real.
* **Historial de actividades:** un sector dedicado a listar las últimas acciones realizadas, brindando un contexto temporal de los cuidados recibidos.

<img width="300" alt="Pet Care Tracker (6)" src="https://github.com/user-attachments/assets/1761c4ff-fb99-480a-a7a4-c40d0f38be1e" />

## Utilidades y valor agregado:

* **Persistencia de estado:** para que la aplicación sea verdaderamente útil en el día a día, se implementó un sistema de almacenamiento. Esto garantiza que, una vez que el usuario edita el peso o la edad de la mascota, los cambios se mantengan guardados permanentemente (en un futuro en su perfil de la cuenta logueada) para no perder datos y poder ir haciendo un seguimiento.
* **Adaptabilidad visual:** la interfaz cuenta con soporte para **modo oscuro**. Esta utilidad permite que la aplicación sea cómoda de usar en condiciones de poca luz teniendo el teléfono con dark mode activado, adaptando automáticamente los colores de fondo y texto según la configuración del sistema operativo del usuario.

---

Pet Care Tracker logra combinar un diseño visualmente atractivo con una funcionalidad práctica. La transición desde el mockup hasta la aplicación funcional en Android Studio demuestra un **flujo de trabajo coherente**, donde cada componente de la pantalla tiene una razón de ser y una utilidad clara para el cuidado de la mascota.

Progresión: La app esta pensada para tener una proyección a futuro, donde se tenga una pantalla con los distintos perfiles de cada mascota, y sectores donde se registre tracker semanal para poder ir chequeando progresos y actividades.

## Demo app Pet Care Tracker:
https://drive.google.com/drive/folders/1jq76qCbzTChJ61su1-jqfGMpfrAnAH1W?usp=sharing
