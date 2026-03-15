package model;

import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Usuario {

    private String nome;

    private String email;

    private LocalDate dataNascimento;

    private String senhaHash;

    private LocalDateTime diaCriacaoConta;

    public Usuario() {
    }

    public Usuario(String nome, String email, LocalDate dataNascimento, String senhaHash) {
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.diaCriacaoConta = LocalDateTime.now();
        setSenhaHash(senhaHash);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = BCrypt.hashpw(senhaHash, BCrypt.gensalt());
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", senhaHash='" + senhaHash + '\'' +
                ", diaCriacaoConta=" + diaCriacaoConta +
                '}';
    }
}
