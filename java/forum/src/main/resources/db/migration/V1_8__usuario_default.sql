INSERT INTO USUARIO(nome, email, senha) VALUES('Aluno', 'aluno@email.com', '$2a$12$bmELxNmoV4O2ZVlQX71wJutmnxXktKoyv674CRn.o06rtpUCH09hy');

INSERT INTO USUARIO(nome, email, senha) VALUES('Moderador', 'moderador@email.com', '$2a$12$bmELxNmoV4O2ZVlQX71wJutmnxXktKoyv674CRn.o06rtpUCH09hy');

INSERT INTO USUARIO_PERFIS(usuario_id, perfis_id)
VALUES (
        (select id from usuario where email = 'aluno@email.com'),
        (select id from perfil where nome = 'ROLE_ALUNO')
);

INSERT INTO USUARIO_PERFIS(usuario_id, perfis_id)
VALUES (
        (select id from usuario where email = 'moderador@email.com'),
        (select id from perfil where nome = 'ROLE_MODERADOR')
);