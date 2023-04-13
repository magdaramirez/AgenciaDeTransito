/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.utils;

import java.util.Calendar;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.itson.dominio.Persona;

/**
 *
 * @author magda
 */
public class Validadores {

    /**
     * Método que valida que los textos no estén vacíos.
     *
     * @param texto Cadena de texto.
     * @return Verdadero en caso de estar vacío, falso en caso contrario.
     */
    public static boolean esTextoVacio(String texto) {
        if (texto == null) {
            return true;
        }
        if (texto.trim().isEmpty()) { //Misma función que el isBlank
            return true;
        }
        return false;
    }

    /**
     * Método que valida que el teléfono contenga de 1 a 10 caracteres, siendo
     * estos números.
     *
     * @param s Cadena de texto.
     * @return coincidencia entre los validadores y la cadena de texto.
     */
    public boolean esTelefono(String s) {
        String patron = "([0-9]){1,10}";

        Pattern p = Pattern.compile(patron);

        Matcher matcher = p.matcher(s);

        return matcher.matches();
    }

    /**
     * Método que valida que la cadena de texto tenga el formato de la clave
     * única de Registro Federal de Contribuyentes.
     *
     * @param texto Cadena de texto.
     * @return coincidencia entre los validadores y la cadena de texto.
     */
    public static boolean esRFC(String texto) {
        CharSequence cadena = texto.trim();
        String reCadena = "[A-Z,Ñ,&]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[A-Z|\\d]{3}";

        Pattern pattern = Pattern.compile(reCadena);
        Matcher matcher = pattern.matcher(cadena);

        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método que valida que la cadena de texto tenga el formato de la Clave
     * Única de Registro de Población.
     *
     * @param texto Cadena de texto.
     * @return coincidencia entre los validadores y la cadena de texto.
     */
    public static boolean esCURP(String texto) {
        CharSequence cadena = texto.trim();
        String reCadena = "^([A-Z&]|[a-z&]{1})([AEIOU]|[aeiou]{1})([A-Z&]|[a-z&]{1})([A-Z&]|[a-z&]{1})([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])([HM]|[hm]{1})([AS|as|BC|bc|BS|bs|CC|cc|CS|cs|CH|ch|CL|cl|CM|cm|DF|df|DG|dg|GT|gt|GR|gr|HG|hg|JC|jc|MC|mc|MN|mn|MS|ms|NT|nt|NL|nl|OC|oc|PL|pl|QT|qt|QR|qr|SP|sp|SL|sl|SR|sr|TC|tc|TS|ts|TL|tl|VZ|vz|YN|yn|ZS|zs|NE|ne]{2})([^A|a|E|e|I|i|O|o|U|u]{1})([^A|a|E|e|I|i|O|o|U|u]{1})([^A|a|E|e|I|i|O|o|U|u]{1})([0-9]{2})$";

        Pattern pattern = Pattern.compile(reCadena);
        Matcher matcher = pattern.matcher(cadena);

        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método que valida que la cadena de texto tenga el formato de la placa de
     * un automóvil.
     *
     * @param texto Cadena de texto.
     * @return coincidencia entre los validadores y la cadena de texto.
     */
    public static boolean esPlaca(String texto) {
        CharSequence cadena = texto.trim();
        String reCadena = "([A-Z]{3}[-][0-9]{3}[-][A-Z]{1})";

        Pattern pattern = Pattern.compile(reCadena);
        Matcher matcher = pattern.matcher(cadena);

        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método que valida que la cadena de texto no exceda el límite de
     * caracteres.
     *
     * @param texto Cadena de texto.
     * @param limiteCaracteres número entero del límite de caracteres.
     * @return Verdadero en caso de exceder el límite, falso en caso contrario.
     */
    public static boolean excedeLimite(String texto, int limiteCaracteres) {
        if (texto == null || texto.length() > limiteCaracteres) {
            return true;
        }
        return false;
    }

    /**
     * Método que valida que la cadena de texto tenga el formato del número de
     * serie de un automóvil.
     *
     * @param texto Cadena de texto.
     * @return coincidencia entre los validadores y la cadena de texto.
     */
    public static boolean esNoSerie(String texto) {
        CharSequence cadena = texto.trim();
        String reCadena = "([A-Z]{3}[-][0-9]{3})";

        Pattern pattern = Pattern.compile(reCadena);
        Matcher matcher = pattern.matcher(cadena);

        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método que valida si una persona es mayor de edad.
     * @param persona Persona.
     * @return Verdadero si es menor de edad, falso si es mayor de edad.
     */
    public static boolean esMayorDeEdad(Persona persona) {
        Calendar nacimiento = Calendar.getInstance();
        nacimiento.set(persona.getFechaNacimiento().get(YEAR), persona.getFechaNacimiento().get(MONTH), persona.getFechaNacimiento().get(DAY_OF_MONTH));

        Calendar hoy = Calendar.getInstance();

        int edad = hoy.get(YEAR) - nacimiento.get(YEAR);
        if (edad < 18) {
            return true;
        } else {
            return false;
        }
    }

}
