create table usuario_perfis
(
    usuario_id bigint not null,
    perfis_id  bigint not null
);

alter table usuario_perfis
    add constraint FK7bhs80brgvo80vhme3u8m6ive
        foreign key (perfis_id)
            references perfil;

alter table usuario_perfis
    add constraint FKs91tgiyagbilt959wbufiphgc
        foreign key (usuario_id)
            references usuario;