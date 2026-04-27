package br.com.katia.quarkus.apibancaria.resource;

import org.mindrot.jbcrypt.BCrypt;

public class HashGenerator {

    public static void main(String[] args) {

        String senha = "senha123";

        String hash = BCrypt.hashpw(senha, BCrypt.gensalt());

        System.out.println("HASH GERADO:");
        System.out.println(hash);

        System.out.println("TAMANHO: " + hash.length());
    }
}