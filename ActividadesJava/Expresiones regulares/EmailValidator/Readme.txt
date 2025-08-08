# Validador de Direcciones de Email

## Descripción
Programa Java para validar direcciones de email en varios formatos:
- Emails individuales
- Listas separadas por comas
- Grupos de emails separados por guiones
- Cualquier combinación de los anteriores con espacios opcionales

## Autor
Kevin Gómez Valderas (2ºDAM)

## Características principales
- Valida el formato estándar de emails
- Soporta múltiples formatos:
  * Email simple: usuario@dominio.com
  * Listas: usuario1@dominio.com, usuario2@dominio.com
  * Grupos: usuario1@dominio.com - usuario2@dominio.com
  * Formatos mixtos
- Maneja espacios opcionales alrededor de separadores
- Proporciona resultados claros de validación

## Reglas de validación
1. Formato de email:
   - Parte local: [a-zA-Z0-9._%+-]+
   - Dominio: [a-zA-Z0-9.-]+
   - TLD: \.[a-zA-Z]{2,}
2. Separadores:
   - Coma (,) con espacios opcionales
   - Guión (-) con espacios opcionales
3. Múltiples emails deben seguir las mismas reglas

## Patrón Regex
```regex
([a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,})(\s*,\s*[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,})*(\s*-\s*([a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}(\s*,\s*[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,})*)*
```

## Casos de prueba incluidos
- Email individual
- Listas separadas por comas
- Grupos separados por guiones
- Formatos mixtos
- Casos inválidos (para demostración)

## Ejemplo de salida
```
Entrada email1@ejemplo.com - email2@ejemplo.com --------- VALIDA.
Entrada email1@ejemplo.com, email2@ejemplo.com --------- VALIDA.
Entrada email1@ejemplo.com email2@ejemplo.com --------- NO VALIDA.
```

## Cómo usar
1. Compilar: `javac emailvalidator/EmailValidator.java`
2. Ejecutar: `java emailvalidator.EmailValidator`

Para añadir más casos de prueba:
- Modificar el array 'inputs' en el método main

## Requisitos
- Java 8 o superior
- Sin dependencias externas

## Notas importantes
- Valida el formato pero NO verifica:
  * Validez DNS de dominios
  * Existencia real del email
  * Casos especiales como partes locales entre comillas
- La validación de TLD es simplificada (2+ letras)

## Versión
1.0 - Versión inicial con validación completa de formatos de email