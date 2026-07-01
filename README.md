# TP2 Aplicaciones Móviles - "Pet Care Tracker"

**Institución:** Escuela de Arte Multimedial Da Vinci  
**Materia:** Aplicaciones Móviles  
**Integrante:** Paloma Gareis Borgiani 

**Informe del proyecto:** "Pet Care Tracker" es una aplicación mobile diseñada para la gestión y seguimiento de la salud y rutina diaria de una mascota. Surge de la necesidad de centralizar y gestionar la información esencial de una mascota en una plataforma móvil. El objetivo principal es centralizar la información principal del animal (edad, peso, actividades) en una interfaz intuitiva que garantice la **persistencia de los datos** y en donde se pueda hacer un seguimiento del mismo, colocando información sobre el día a día de la mascota.

Para el desarrollo de la interfaz se trabajó bajo un **concepto minimalista y orgánico**. La elección de la paleta de colores, como tonos verdes suaves y crema, busca transmitir una sensación de bienestar y cuidado animal.

## 1. Introducción y Arquitectura del Proyecto
A nivel técnico, me aseguré de que el proyecto respete estrictamente las buenas prácticas de desarrollo en Android, dando cumplimiento a los requisitos obligatorios de la cátedra:

* **Organización de Recursos:** Independicé el diseño visual de la lógica de programación. Todos los textos, tamaños y paletas los aislé de forma apropiada en `strings.xml` (incluyendo formateo HTML con etiquetas `<b>` para resaltados), `dimens.xml` y `colors.xml`, garantizando un código limpio.
* **Diseño de Interfaz (UI/UX) y Colores:** Definí una paleta cromática armónica. Respecto a entregas anteriores, estilicé los botones y estructuré la información analítica en filas modulares con bordes redondeados, mejorando drásticamente la legibilidad y la estética visual.
* **Control de Versiones:** Al ser un proyecto individual, gestioné el repositorio en su totalidad, realizando múltiples *commits* iterativos a lo largo del desarrollo. Además, utilicé la convención **Conventional Commits** (ej: `feat`, `fix`, `ui`) para documentar claramente la evolución del código.

---

## 2. Flujo de Navegación y Pasaje de Datos
La aplicación cuenta con varias pantallas que permiten una navegación robusta y fluida entre las distintas características del sistema (`LoginActivity`, `RegisterActivity`, `SetupPetActivity`, `MainActivity`, `ProfileActivity`).

**Pasaje de Datos y Persistencia:** La comunicación entre `Activities` la realizo a través de `Intents`. Para la gestión de sesión y pasaje de estado de las ventanas, utilizo banderas de optimización de pila (`FLAG_ACTIVITY_CLEAR_TOP`). El estado y los datos sensibles (perfil de la mascota) los centralicé a través de la implementación de **Firebase Authentication** y **Cloud Firestore**, permitiendo que cada activity recupere los datos en tiempo real (ID de usuario, nombre, especie, peso) sin necesidad de sobrecargar la memoria con envíos masivos de paquetes temporales (`Extras`).

---

## 3. Descripción Funcional por Pantallas

### Pantalla de bienvenida: Splash Screen
      
Se mantuvo la implementación de una **Splash Screen** como punto de entrada a la aplicación para que al iniciarla se pueda visualizar el nombre y logo de la app, teniendo una transición que dura unos segundos para luego pasar a la pantalla principal, generando una mejor experiencia UX.
<img width="300" alt="Pet Care Tracker (1)" src="https://github.com/user-attachments/assets/de03c867-ec19-4d15-afec-c8e0fcba002a" />

### Pantalla 1: Login / Autenticación (`LoginActivity.java`)
<img width="300" alt="Screenshot_20260701_181406" src="https://github.com/user-attachments/assets/e9b19442-1341-409f-ab7a-bc1b0b432d5c" />

* **Funcionalidad Esperada:** Permitir el ingreso seguro del usuario o derivarlo al registro.
* **Comportamiento Dinámico:** Implementé **Firebase Auth** para verificar las credenciales. Al ingresar correctamente, el sistema enruta al usuario al Dashboard. Un `TextView` con formato interactivo (y un evento de escucha aplicado) permite redirigir a los usuarios nuevos hacia el registro.
* **Layouts y Views:** Hice uso de `ConstraintLayout` para centrar los elementos de manera responsiva, utilizando `EditText` para credenciales y `Button` para la acción principal.

### Pantalla 2: Creación de Perfil (`RegisterActivity.java`)
<img width="300" alt="Screenshot_20260701_181444" src="https://github.com/user-attachments/assets/c1f8ee95-89b1-4fe9-a991-def8b172a8ae" />

* **Funcionalidad Esperada:** Permitir el registro de un nuevo usuario en el sistema.
* **Comportamiento Dinámico y Firebase:** Mediante un evento aplicado al botón de registro, capturo los datos de los campos de texto e implemento **Firebase Authentication** para crear la nueva cuenta. Una vez validada por la base de datos, el flujo de navegación deriva automáticamente a la pantalla de configuración inicial de la mascota.
* **Layouts y Views:** Implementé un diseño estructurado con `ConstraintLayout`, `EditText` para los datos de registro, y un `Button` para accionar el evento.

### Pantalla 3: Registro de la Mascota (`SetupPetActivity.java`)
<img width="300" alt="Screenshot_20260701_181720" src="https://github.com/user-attachments/assets/5f83f3e5-51b1-42a2-922a-f152d18dd856" />

* **Funcionalidad Esperada:** Recopilar los datos iniciales y reales del animal para el nuevo usuario.
* **Comportamiento Dinámico y Firebase:** Al interactuar con el botón "Guardar", la aplicación captura los contenidos reales ingresados (nombre, especie, edad, peso) y los almacena permanentemente utilizando **Cloud Firestore**. Para evitar cruce de información, creo un documento único en la colección utilizando el ID exclusivo del usuario (`UID`).
* **Layouts y Views:** Uso de `TextViews` para las etiquetas y `EditText` para los datos, organizados prolijamente mediante `LinearLayouts` apilados dentro de un `ConstraintLayout` principal.

### Pantalla 4: Dashboard Principal (`MainActivity.java`)
<img width="300" alt="Screenshot_20260701_181524" src="https://github.com/user-attachments/assets/6d499346-89a6-4f06-af23-6f19dd429043" />
<img width="300" alt="Screenshot_20260701_181547" src="https://github.com/user-attachments/assets/297acf52-762c-4721-97f9-76b0e1ae57ee" />

* **Funcionalidad Esperada:** Es la pantalla central operativa. Muestra los datos de la mascota y permite registrar actividades en tiempo real.
* **Contenidos Reales e Imágenes:** Muestro en pantalla los datos verídicos obtenidos desde Firestore y agregué una imagen genérica representativa en un `ImageView` con recorte de bordes (`setClipToOutline`) para enriquecer visualmente el producto.
* **Comportamiento Dinámico y Eventos:** Esta pantalla cumple un rol dinámico fundamental. Desarrollé un panel de botones funcionales (Comida, Agua, Paseo, etc.). Al aplicar el **evento de clic** sobre ellos, la aplicación interactúa calculando la hora actual y **generando vistas dinámicamente**: inyecta un nuevo `TextView` en la posición 0 dentro de un contenedor en un `ScrollView`. Esto permite visualizar un historial autogenerado. Aislé la persistencia de este historial utilizando el UID del usuario (`SharedPreferences` segmentado).
* **Layouts:** Utilicé un `ConstraintLayout` como raíz, interactuando con `LinearLayouts` verticales y horizontales para agrupar los botones de acción y los elementos del historial.

### Pantalla 5: Perfil de Usuario y Estadísticas (`ProfileActivity.java`)
<img width="300" alt="Screenshot_20260701_181622" src="https://github.com/user-attachments/assets/7fc1ac5e-a157-46a2-9c87-1f41a6044eb0" />


* **Funcionalidad Esperada:** Mostrar preferencias de la cuenta, un resumen estadístico de las actividades y permitir el cierre de sesión.
* **Comportamiento Dinámico y Eventos:** 1. *Cálculo en Tiempo Real:* La pantalla lee el archivo de registros del usuario específico y responde modificando un `TextView` con el cálculo exacto de la cantidad de actividades realizadas.
  2. *Interactividad:* Implementé una lista de componentes tipo `Switch` (preferencias y tips) que cuentan con eventos `OnCheckedChangeListener`. Al interactuar, la interfaz reacciona devolviendo un feedback visual (`Toast`) al usuario indicando la modificación.
* **Layouts:** Diseño modular utilizando un `ConstraintLayout` que envuelve diversos `LinearLayouts` (vertical y horizontal). Esto permite que el bloque de estadísticas y las preferencias se agrupen de forma escalable.
* **Navegación Superior:** Implementé un menú de opciones (*Toolbar*) inflando un archivo XML propio, otorgando atajos rápidos de navegación y un evento para cerrar la sesión con Firebase de forma segura.

---

Pet Care Tracker logra combinar un diseño visualmente atractivo con una funcionalidad práctica. La transición desde el mockup hasta la aplicación funcional en Android Studio demuestra un **flujo de trabajo coherente**, donde cada componente de la pantalla tiene una razón de ser y una utilidad clara para el cuidado de la mascota.

## Progresión y Roadmap (Versión 2.0)
Como parte de la evolución del proyecto, he planificado una serie de mejoras técnicas y funcionales que potenciarán la experiencia del usuario en futuras versiones:

* **Imágenes dinámicas en la nube:** Integración de la biblioteca **Glide** para descargar y renderizar fotografías reales de la mascota desde *Firebase Storage*, reemplazando la imagen vectorial genérica del panel principal.
* **Gestión multimascota:** Expansión de la interfaz y la base de datos para permitir el registro y seguimiento simultáneo de varios animales, implementando un carrusel deslizable en el *Dashboard* para alternar entre perfiles.
* **Módulo de enriquecimiento y cuidado natural:** Incorporación de categorías avanzadas en el historial para registrar rutinas específicas de bienestar integral, como sesiones de aseo con productos naturales o el uso de alfombras de lamido (*lick mats*) para reducir la ansiedad.
* **Mejoras de UI en estadísticas:** Implementación de componentes visuales superpuestos (como miniaturas circulares con la foto real del animal) dentro de las tarjetas de la pantalla de perfil para lograr un diseño más inmersivo y personalizado.

## Demo app Pet Care Tracker:
https://drive.google.com/drive/folders/1jq76qCbzTChJ61su1-jqfGMpfrAnAH1W?usp=sharing
