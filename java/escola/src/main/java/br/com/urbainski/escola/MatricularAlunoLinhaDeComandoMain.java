package br.com.urbainski.escola;

import br.com.urbainski.escola.aplicacao.aluno.matricular.MatricularAluno;
import br.com.urbainski.escola.aplicacao.aluno.matricular.MatricularAlunoDTO;
import br.com.urbainski.escola.dominio.aluno.AlunoMatriculadoLog;
import br.com.urbainski.escola.dominio.aluno.AlunoRepository;
import br.com.urbainski.escola.dominio.aluno.TelefoneRepository;
import br.com.urbainski.escola.dominio.evento.PublicadorEvento;
import br.com.urbainski.escola.dominio.evento.PublicadorEventoDefault;
import br.com.urbainski.escola.infra.aluno.AlunoRepositoryJDBCImpl;
import br.com.urbainski.escola.infra.aluno.TelefoneRepositoryJDBCImpl;
import br.com.urbainski.escola.infra.database.impl.H2DatabaseConnection;
import br.com.urbainski.escola.util.Constantes;

import java.util.Scanner;

public class MatricularAlunoLinhaDeComandoMain {

    public static void main(String[] args) throws Exception {

        System.setProperty(Constantes.DB_NAME_PARAM, "./escola.db");

        try (var connection = H2DatabaseConnection.getInstance().getConnection()) {

            TelefoneRepository telefoneRepository = new TelefoneRepositoryJDBCImpl(connection);
            AlunoRepository alunoRepository = new AlunoRepositoryJDBCImpl(connection, telefoneRepository);

            PublicadorEvento publicadorEventos = PublicadorEventoDefault.getInstance();
            publicadorEventos.adicionar(new AlunoMatriculadoLog());

            var matricularAluno = new MatricularAluno(alunoRepository, publicadorEventos);

            try (var scanner = new Scanner(System.in)) {

                System.out.println("Informe o nome do aluno:");

                var nome = scanner.nextLine();

                System.out.println("Informe o email do aluno:");

                var email = scanner.nextLine();

                System.out.println("Informe o cpf do aluno:");

                var cpf = scanner.nextLine();

                var dto = new MatricularAlunoDTO(nome, cpf, email);

                matricularAluno.executar(dto);
            }
        }

    }

}
