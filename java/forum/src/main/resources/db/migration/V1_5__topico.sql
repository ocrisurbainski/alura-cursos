create table topico
(
    id           bigint generated by default as identity,
    data_criacao timestamp,
    mensagem     varchar(255),
    status       varchar(255),
    titulo       varchar(255),
    autor_id     bigint,
    curso_id     bigint,
    primary key (id)
);

alter table topico
    add constraint FKsk04hscorwqdymnafg8882v64
        foreign key (autor_id)
            references usuario;

alter table topico
    add constraint FKcaaogjo0ynd54updie6kdpxd1
        foreign key (curso_id)
            references curso;