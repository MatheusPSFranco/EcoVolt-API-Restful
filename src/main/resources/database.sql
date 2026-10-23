--Drops
DROP TABLE T_EV_ACA_SUS_MIS CASCADE CONSTRAINTS ;
DROP TABLE T_EV_ACAO_SUSTENTAVEIS CASCADE CONSTRAINTS ;
DROP TABLE T_EV_CONQUISTA CASCADE CONSTRAINTS ;
DROP TABLE T_EV_CONTA_ENERGIA CASCADE CONSTRAINTS ;
DROP TABLE T_EV_ENV_ACA_MIS CASCADE CONSTRAINTS ;
DROP TABLE T_EV_ENVIO_ACAO CASCADE CONSTRAINTS ;
DROP TABLE T_EV_INTERACAO CASCADE CONSTRAINTS ;
DROP TABLE T_EV_LOGIN_DIARIO CASCADE CONSTRAINTS ;
DROP TABLE T_EV_MISSAO CASCADE CONSTRAINTS ;
DROP TABLE T_EV_NIVEL CASCADE CONSTRAINTS ;
DROP TABLE T_EV_ODS CASCADE CONSTRAINTS ;
DROP TABLE T_EV_ODS_MIS CASCADE CONSTRAINTS ;
DROP TABLE T_EV_PONTUACAO CASCADE CONSTRAINTS ;
DROP TABLE T_EV_RANKING CASCADE CONSTRAINTS ;
DROP TABLE T_EV_RECOMPENSA CASCADE CONSTRAINTS ;
DROP TABLE T_EV_RECUPERACAO_LOGIN CASCADE CONSTRAINTS ;
DROP TABLE T_EV_RECURSO_REVISAO CASCADE CONSTRAINTS ;
DROP TABLE T_EV_TIER CASCADE CONSTRAINTS ;
DROP TABLE T_EV_USU_CON CASCADE CONSTRAINTS ;
DROP TABLE T_EV_USU_MIS CASCADE CONSTRAINTS ;
DROP TABLE T_EV_USUARIO CASCADE CONSTRAINTS ;
DROP TABLE T_EV_VALIDACAO CASCADE CONSTRAINTS ;

CREATE TABLE T_EV_ACA_SUS_MIS 
    ( 
     id_acao        NUMBER (10)  NOT NULL , 
     id_missao      NUMBER (10)  NOT NULL , 
     qt_necessaria  NUMBER (5) DEFAULT 1  NOT NULL , 
     ob_aca_sus_mis CHAR (1) DEFAULT 'S'  NOT NULL 
    )
;

ALTER TABLE T_EV_ACA_SUS_MIS 
    ADD CONSTRAINT ck_am_qtd 
    CHECK (qt_necessaria > 0)
;


ALTER TABLE T_EV_ACA_SUS_MIS 
    ADD CONSTRAINT ck_am_obrig 
    CHECK (ob_aca_sus_mis IN ('S', 'N'))
;
ALTER TABLE T_EV_ACA_SUS_MIS 
    ADD CONSTRAINT acoes_sustentaveis_missoes_PK PRIMARY KEY ( id_acao, id_missao ) ;
    
CREATE SEQUENCE SQ_EV_ACA_SUS_MIS START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;

CREATE TABLE T_EV_ACAO_SUSTENTAVEIS 
    ( 
     id_acao             NUMBER (10)  NOT NULL , 
     id_ods              NUMBER (10)  NOT NULL , 
     nm_acao             VARCHAR2 (150)  NOT NULL , 
     dc_acao_sustentavel VARCHAR2 (500) , 
     df_acao_sustentavel VARCHAR2 (20) , 
     im_ambiental        NUMBER (5,2) , 
     sf_necessario       NUMBER (5,2) , 
     fq_recomendada      VARCHAR2 (80) , 
     pt_base             NUMBER (3)  NOT NULL , 
     lm_diario           NUMBER (3) , 
     st_acao             VARCHAR2 (20) DEFAULT 'ATIVA'  NOT NULL 
    ) 
;

ALTER TABLE T_EV_ACAO_SUSTENTAVEIS 
    ADD CONSTRAINT ck_acao_dif 
    CHECK (df_acao_sustentavel IN ('FACIL', 'MEDIA', 'DIFICIL'))
;


ALTER TABLE T_EV_ACAO_SUSTENTAVEIS 
    ADD CONSTRAINT ck_acao_pts 
    CHECK (pt_base BETWEEN 0 AND 100)
;


ALTER TABLE T_EV_ACAO_SUSTENTAVEIS 
    ADD CONSTRAINT ck_acao_imp 
    CHECK (im_ambiental BETWEEN 0 AND 100)
;


ALTER TABLE T_EV_ACAO_SUSTENTAVEIS 
    ADD CONSTRAINT ck_acao_esf 
    CHECK (sf_necessario BETWEEN 0 AND 100)
;


ALTER TABLE T_EV_ACAO_SUSTENTAVEIS 
    ADD CONSTRAINT ck_acao_status 
    CHECK (st_acao IN ('ATIVA', 'INATIVA'))
;
ALTER TABLE T_EV_ACAO_SUSTENTAVEIS 
    ADD CONSTRAINT acoes_sustentaveis_PK PRIMARY KEY ( id_acao ) ;

CREATE SEQUENCE SQ_EV_ACAO_SUSTENTAVEIS START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;
    
CREATE TABLE T_EV_CONQUISTA 
    ( 
     id_conquista   NUMBER (10)  NOT NULL , 
     id_nivel       NUMBER (10) , 
     nm_conquista   VARCHAR2 (150)  NOT NULL , 
     dc_conquista   VARCHAR2 (500) , 
     tp_conquista   VARCHAR2 (30)  NOT NULL , 
     cr_desbloqueio VARCHAR2 (500) , 
     qt_necessaria  NUMBER (10) , 
     md_conquista   VARCHAR2 (80) , 
     st_conquista   VARCHAR2 (20) DEFAULT 'ATIVA'  NOT NULL 
    ) 
;

ALTER TABLE T_EV_CONQUISTA 
    ADD CONSTRAINT ck_conq_tipo 
    CHECK (tp_conquista IN ('ACAO', 'MISSAO', 'LOGIN', 'INTERACAO', 'RANKING', 'ODS'))
;


ALTER TABLE T_EV_CONQUISTA 
    ADD CONSTRAINT ck_conq_status 
    CHECK (st_conquista IN ('ATIVA', 'INATIVA'))
;


ALTER TABLE T_EV_CONQUISTA 
    ADD CONSTRAINT ck_conq_qtd 
    CHECK (qt_necessaria IS NULL OR qt_necessaria >= 0)
;
ALTER TABLE T_EV_CONQUISTA 
    ADD CONSTRAINT conquistas_PK PRIMARY KEY ( id_conquista ) ;
    
CREATE SEQUENCE SQ_EV_CONQUISTA START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;
    
CREATE TABLE T_EV_CONTA_ENERGIA 
    ( 
     id_conta_energia NUMBER (10)  NOT NULL , 
     id_usuario       NUMBER (10)  NOT NULL , 
     id_recompensa    NUMBER (10) , 
     nm_titular       VARCHAR2 (120)  NOT NULL , 
     cc_conta_energia VARCHAR2 (120)  NOT NULL , 
     nr_instalacao    VARCHAR2 (80) , 
     ms_referencia    VARCHAR2 (7)  NOT NULL , 
     vl_conta         NUMBER (10,2)  NOT NULL , 
     vl_subsidiado    NUMBER (10,2) DEFAULT 0  NOT NULL , 
     rq_comprovante   VARCHAR2 (500) , 
     st_pagamento     VARCHAR2 (20) DEFAULT 'PENDENTE'  NOT NULL 
    ) 
;

ALTER TABLE T_EV_CONTA_ENERGIA 
    ADD CONSTRAINT ck_conta_val 
    CHECK (vl_conta >= 0)
;


ALTER TABLE T_EV_CONTA_ENERGIA 
    ADD CONSTRAINT ck_conta_sub 
    CHECK (vl_subsidiado >= 0)
;


ALTER TABLE T_EV_CONTA_ENERGIA 
    ADD CONSTRAINT ck_conta_status 
    CHECK (st_pagamento IN ('PENDENTE', 'APROVADO', 'PAGO', 'REPROVADO'))
;
ALTER TABLE T_EV_CONTA_ENERGIA 
    ADD CONSTRAINT conta_energia_PK PRIMARY KEY ( id_conta_energia ) ;
    
CREATE SEQUENCE SQ_EV_CONTA_ENERGIA START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;

CREATE TABLE T_EV_ENV_ACA_MIS 
    ( 
     id_envio          NUMBER (10)  NOT NULL , 
     id_missao         NUMBER (10)  NOT NULL , 
     ct_env_aca_mis    CHAR (1) DEFAULT 'N'  NOT NULL , 
     dt_contabilizacao DATE 
    ) 
;

ALTER TABLE T_EV_ENV_ACA_MIS 
    ADD CONSTRAINT ck_em_contab 
    CHECK (ct_env_aca_mis IN ('S', 'N'))
;
ALTER TABLE T_EV_ENV_ACA_MIS 
    ADD CONSTRAINT envio_acao_missoes_PK PRIMARY KEY ( id_envio, id_missao ) ;
    
CREATE SEQUENCE SQ_EV_ENV_ACA_MIS START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;
    
CREATE TABLE T_EV_ENVIO_ACAO 
    ( 
     id_envio                       NUMBER (10)  NOT NULL , 
     T_EV_USUARIO_id_usuario        NUMBER (10)  NOT NULL , 
     T_EV_ACAO_SUSTENTAVEIS_id_acao NUMBER (10)  NOT NULL , 
     dt_envio                       DATE DEFAULT SYSDATE  NOT NULL , 
     tp_evidencia                   VARCHAR2 (30) , 
     rq_evidencia                   VARCHAR2 (500) , 
     st_envio                       VARCHAR2 (20) DEFAULT 'PENDENTE'  NOT NULL , 
     pt_prevista                    NUMBER (3) , 
     ob_usuario                     VARCHAR2 (500) 
    ) 
;

ALTER TABLE T_EV_ENVIO_ACAO 
    ADD CONSTRAINT ck_env_tipo 
    CHECK (tp_evidencia IN ('FOTO', 'VIDEO', 'DOCUMENTO', 'TEXTO'))
;


ALTER TABLE T_EV_ENVIO_ACAO 
    ADD CONSTRAINT ck_env_status 
    CHECK (st_envio IN ('PENDENTE', 'EM_VALIDACAO', 'APROVADO', 'REPROVADO', 'CANCELADO'))
;


ALTER TABLE T_EV_ENVIO_ACAO 
    ADD CONSTRAINT ck_env_pts 
    CHECK (pt_prevista IS NULL OR pt_prevista BETWEEN 0 AND 100)
;
ALTER TABLE T_EV_ENVIO_ACAO 
    ADD CONSTRAINT envio_acao_PK PRIMARY KEY ( id_envio ) ;

CREATE SEQUENCE SQ_EV_ENVIO_ACAO START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;

CREATE TABLE T_EV_INTERACAO 
    ( 
     id_interacao   NUMBER (10)  NOT NULL , 
     id_usuario     NUMBER (10)  NOT NULL , 
     tp_interacao   VARCHAR2 (30)  NOT NULL , 
     dt_interacao   DATE DEFAULT SYSDATE  NOT NULL , 
     ps_interacao   NUMBER (5,2) DEFAULT 1  NOT NULL , 
     nt_valida      CHAR (1) DEFAULT 'S'  NOT NULL , 
     sp_detectado   CHAR (1) DEFAULT 'N'  NOT NULL , 
     cn_relacionado VARCHAR2 (500) 
    ) 
;

ALTER TABLE T_EV_INTERACAO 
    ADD CONSTRAINT ck_int_tipo 
    CHECK (tp_interacao IN ('CURTIDA', 'COMENTARIO', 'POSTAGEM', 'COMPARTILHAMENTO'))
;


ALTER TABLE T_EV_INTERACAO 
    ADD CONSTRAINT ck_int_peso 
    CHECK (ps_interacao >= 0)
;


ALTER TABLE T_EV_INTERACAO 
    ADD CONSTRAINT ck_int_valida 
    CHECK (nt_valida IN ('S', 'N'))
;


ALTER TABLE T_EV_INTERACAO 
    ADD CONSTRAINT ck_int_spam 
    CHECK (sp_detectado IN ('S', 'N'))
;
ALTER TABLE T_EV_INTERACAO 
    ADD CONSTRAINT interacoes_PK PRIMARY KEY ( id_interacao ) ;

CREATE SEQUENCE SQ_EV_INTERACAO START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;
    
CREATE TABLE T_EV_LOGIN_DIARIO 
    ( 
     id_login       NUMBER (10)  NOT NULL , 
     id_usuario     NUMBER (10)  NOT NULL , 
     dt_login       DATE  NOT NULL , 
     tp_uso_minutos NUMBER (5) DEFAULT 0  NOT NULL , 
     lg_valido      CHAR (1) DEFAULT 'N'  NOT NULL , 
     sq_atual       NUMBER (5) DEFAULT 0  NOT NULL , 
     bn_gerado      NUMBER (10) DEFAULT 0  NOT NULL , 
     nt_exibida     CHAR (1) DEFAULT 'N'  NOT NULL 
    ) 
;

ALTER TABLE T_EV_LOGIN_DIARIO 
    ADD CONSTRAINT ck_log_tempo 
    CHECK (tp_uso_minutos >= 0)
;


ALTER TABLE T_EV_LOGIN_DIARIO 
    ADD CONSTRAINT ck_log_valido 
    CHECK (lg_valido IN ('S', 'N'))
;


ALTER TABLE T_EV_LOGIN_DIARIO 
    ADD CONSTRAINT ck_log_seq 
    CHECK (sq_atual >= 0)
;


ALTER TABLE T_EV_LOGIN_DIARIO 
    ADD CONSTRAINT ck_log_bonus 
    CHECK (bn_gerado >= 0)
;
ALTER TABLE T_EV_LOGIN_DIARIO 
    ADD CONSTRAINT ck_log_notif 
    CHECK (nt_exibida IN ('S', 'N'))
;
ALTER TABLE T_EV_LOGIN_DIARIO 
    ADD CONSTRAINT login_diario_PK PRIMARY KEY ( id_login ) ;

ALTER TABLE T_EV_LOGIN_DIARIO 
    ADD CONSTRAINT login_diario_usuarios_UN UNIQUE ( id_usuario , dt_login ) ;

CREATE SEQUENCE SQ_EV_LOGIN_DIARIO START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;

CREATE TABLE T_EV_MISSAO 
    ( 
     id_missao     NUMBER (10)  NOT NULL , 
     nm_missao     VARCHAR2 (150)  NOT NULL , 
     dc_missao     VARCHAR2 (500) , 
     df_missao     VARCHAR2 (20) , 
     pt_recompensa NUMBER (5) DEFAULT 0  NOT NULL , 
     xp_recompensa NUMBER (5) DEFAULT 0  NOT NULL , 
     qt_semanal    NUMBER (2) , 
     tp_missao     VARCHAR2 (30) , 
     st_missao     VARCHAR2 (20) DEFAULT 'ATIVA'  NOT NULL , 
     id_tier       NUMBER (10) , 
     id_nivel      NUMBER (10) 
    ) 
;

ALTER TABLE T_EV_MISSAO 
    ADD CONSTRAINT ck_mis_dif 
    CHECK (df_missao IN ('FACIL', 'MEDIA', 'DIFICIL'))
;


ALTER TABLE T_EV_MISSAO 
    ADD CONSTRAINT ck_mis_status 
    CHECK (st_missao IN ('ATIVA', 'INATIVA'))
;


ALTER TABLE T_EV_MISSAO 
    ADD CONSTRAINT ck_mis_pts 
    CHECK (pt_recompensa >= 0)
;


ALTER TABLE T_EV_MISSAO 
    ADD CONSTRAINT ck_mis_xp 
    CHECK (xp_recompensa >= 0)
;
ALTER TABLE T_EV_MISSAO 
    ADD CONSTRAINT missoes_PK PRIMARY KEY ( id_missao ) ;

CREATE SEQUENCE SQ_EV_MISSAO START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;
    
CREATE TABLE T_EV_NIVEL 
    ( 
     id_nivel  NUMBER (10)  NOT NULL , 
     nr_nivel  NUMBER (5)  NOT NULL , 
     xp_minimo NUMBER (10)  NOT NULL , 
     xp_maximo NUMBER (10)  NOT NULL , 
     dc_nivel  VARCHAR2 (255) , 
     bd_perfil VARCHAR2 (100) , 
     st_nivel  VARCHAR2 (20) DEFAULT 'ATIVO'  NOT NULL 
    ) 
;

ALTER TABLE T_EV_NIVEL 
    ADD CONSTRAINT ck_niv_status 
        CHECK (st_nivel IN ('ATIVO', 'INATIVO'))
;


ALTER TABLE T_EV_NIVEL 
    ADD CONSTRAINT ck_niv_xp 
    CHECK (xp_maximo >= xp_minimo)
;
ALTER TABLE T_EV_NIVEL 
    ADD CONSTRAINT niveis_PK PRIMARY KEY ( id_nivel ) ;

ALTER TABLE T_EV_NIVEL 
    ADD CONSTRAINT niveis_UN UNIQUE ( nr_nivel ) ;
    
CREATE SEQUENCE SQ_EV_NIVEL START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;
    
CREATE TABLE T_EV_ODS 
    ( 
     id_ods NUMBER (10)  NOT NULL , 
     nr_ods NUMBER (2)  NOT NULL , 
     nm_ods VARCHAR2 (120)  NOT NULL , 
     dc_ods VARCHAR2 (500) , 
     ct_ods VARCHAR2 (80) , 
     st_ods VARCHAR2 (20) DEFAULT 'ATIVO'  NOT NULL 
    ) 
;

ALTER TABLE T_EV_ODS 
    ADD CONSTRAINT ck_ods_num 
    CHECK (nr_ods BETWEEN 1 AND 17)
;


ALTER TABLE T_EV_ODS 
    ADD CONSTRAINT ck_ods_status 
    CHECK (st_ods IN ('ATIVO', 'INATIVO'))
;
ALTER TABLE T_EV_ODS 
    ADD CONSTRAINT ods_PK PRIMARY KEY ( id_ods ) ;

ALTER TABLE T_EV_ODS 
    ADD CONSTRAINT ods_numero_ods_UN UNIQUE ( nr_ods ) ;
    
CREATE SEQUENCE SQ_EV_ODS START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;

CREATE TABLE T_EV_ODS_MIS 
    ( 
     id_ods    NUMBER (10)  NOT NULL , 
     id_missao NUMBER (10)  NOT NULL , 
     ps_ods    NUMBER (5,2) 
    ) 
;

ALTER TABLE T_EV_ODS_MIS 
    ADD CONSTRAINT ck_om_peso 
    CHECK (ps_ods IS NULL OR ps_ods BETWEEN 0 AND 100)
;
ALTER TABLE T_EV_ODS_MIS 
    ADD CONSTRAINT ods_missoes_PK PRIMARY KEY ( id_ods, id_missao ) ;
    
CREATE SEQUENCE SQ_EV_ODS_MIS START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;
    
CREATE TABLE T_EV_PONTUACAO 
    ( 
     id_pontuacao  NUMBER (10)  NOT NULL , 
     id_usuario    NUMBER (10)  NOT NULL , 
     id_envio      NUMBER (10) , 
     id_validacao  NUMBER (10) , 
     id_recurso    NUMBER (10) , 
     id_missao     NUMBER (10) , 
     id_login      NUMBER (10) , 
     id_interacao  NUMBER (10) , 
     tp_pontuacao  VARCHAR2 (30)  NOT NULL , 
     pt_obtidos    NUMBER (10) DEFAULT 0  NOT NULL , 
     xp_obtido     NUMBER (10) DEFAULT 0  NOT NULL , 
     dt_pontuacao  DATE DEFAULT SYSDATE  NOT NULL , 
     pr_referencia VARCHAR2 (7) , 
     og_pontuacao  VARCHAR2 (50) , 
     id_nivel      NUMBER (10) 
    ) 
;

ALTER TABLE T_EV_PONTUACAO 
    ADD CONSTRAINT ck_pt_tipo 
    CHECK (tp_pontuacao IN ('ACAO', 'MISSAO', 'LOGIN', 'INTERACAO', 'BONUS'))
;


ALTER TABLE T_EV_PONTUACAO 
    ADD CONSTRAINT ck_pt_pontos 
    CHECK (pt_obtidos >= 0)
;


ALTER TABLE T_EV_PONTUACAO 
    ADD CONSTRAINT ck_pt_xp 
    CHECK (xp_obtido >= 0)
;
ALTER TABLE T_EV_PONTUACAO 
    ADD CONSTRAINT pontuacoes_PK PRIMARY KEY ( id_pontuacao ) ;

CREATE SEQUENCE SQ_EV_PONTUACAO START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;

CREATE TABLE T_EV_RANKING 
    ( 
     id_ranking       NUMBER (10)  NOT NULL , 
     id_usuario       NUMBER (10)  NOT NULL , 
     id_tier          NUMBER (10)  NOT NULL , 
     pr_referencia    VARCHAR2 (7)  NOT NULL , 
     pt_total_periodo NUMBER (10) DEFAULT 0  NOT NULL , 
     pc_ranking       NUMBER (10)  NOT NULL , 
     st_ranking       VARCHAR2 (20) DEFAULT 'ATIVO'  NOT NULL , 
     dt_atualizacao   DATE DEFAULT SYSDATE  NOT NULL 
    ) 
;

ALTER TABLE T_EV_RANKING 
    ADD CONSTRAINT ck_rank_pts 
    CHECK (pt_total_periodo >= 0)
;


ALTER TABLE T_EV_RANKING 
    ADD CONSTRAINT ck_rank_pos 
    CHECK (pc_ranking > 0)
;


ALTER TABLE T_EV_RANKING 
    ADD CONSTRAINT ck_rank_status 
    CHECK (st_ranking IN ('ATIVO', 'FECHADO', 'CANCELADO'))
;
ALTER TABLE T_EV_RANKING 
    ADD CONSTRAINT rankings_PK PRIMARY KEY ( id_ranking ) ;

ALTER TABLE T_EV_RANKING 
    ADD CONSTRAINT rankings_UN UNIQUE ( id_usuario , id_tier , pr_referencia ) ;
    
CREATE SEQUENCE SQ_EV_RANKING START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;

CREATE TABLE T_EV_RECOMPENSA 
    ( 
     id_recompensa NUMBER (10)  NOT NULL , 
     id_usuario    NUMBER (10)  NOT NULL , 
     id_tier       NUMBER (10)  NOT NULL , 
     id_ranking    NUMBER (10)  NOT NULL , 
     tp_recompensa VARCHAR2 (40)  NOT NULL , 
     dc_recompensa VARCHAR2 (500) , 
     vl_recompensa NUMBER (10,2) DEFAULT 0  NOT NULL , 
     vl_maximo     NUMBER (10,2) , 
     st_recompensa VARCHAR2 (20) DEFAULT 'PENDENTE'  NOT NULL , 
     dt_concessao  DATE , 
     pr_referencia VARCHAR2 (7)  NOT NULL 
    ) 
;

ALTER TABLE T_EV_RECOMPENSA 
    ADD CONSTRAINT ck_recomp_tipo 
    CHECK (tp_recompensa IN ('SUBSIDIO_ENERGIA', 'DESCONTO', 'EXPERIENCIA', 'SELO'))
;


ALTER TABLE T_EV_RECOMPENSA 
    ADD CONSTRAINT ck_recomp_status 
    CHECK (st_recompensa IN ('PENDENTE', 'CONCEDIDA', 'UTILIZADA', 'CANCELADA'))
;


ALTER TABLE T_EV_RECOMPENSA 
    ADD CONSTRAINT ck_recomp_val 
    CHECK (vl_recompensa >= 0)
;


ALTER TABLE T_EV_RECOMPENSA 
    ADD CONSTRAINT ck_recomp_max 
    CHECK (vl_maximo IS NULL OR vl_maximo >= 0)
;
ALTER TABLE T_EV_RECOMPENSA 
    ADD CONSTRAINT recompensas_PK PRIMARY KEY ( id_recompensa ) ;
    
CREATE SEQUENCE SQ_EV_RECOMPENSA START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;

CREATE TABLE T_EV_RECUPERACAO_LOGIN 
    ( 
     id_recuperacao      NUMBER (10)  NOT NULL , 
     id_usuario          NUMBER (10)  NOT NULL , 
     id_login            NUMBER (10)  NOT NULL , 
     dt_recuperacao      DATE DEFAULT SYSDATE  NOT NULL , 
     dt_login_recuperado DATE  NOT NULL , 
     ms_referencia       VARCHAR2 (7)  NOT NULL , 
     an_assistido        CHAR (1) DEFAULT 'S'  NOT NULL , 
     ano_referencia      NUMBER (5)  NOT NULL , 
     st_recuperacao      VARCHAR2 (20) DEFAULT 'APROVADA'  NOT NULL 
    ) 
;

ALTER TABLE T_EV_RECUPERACAO_LOGIN 
    ADD CONSTRAINT ck_rl_anuncio 
    CHECK (an_assistido IN ('S', 'N'))
;


ALTER TABLE T_EV_RECUPERACAO_LOGIN 
    ADD CONSTRAINT ck_rl_status 
    CHECK (st_recuperacao IN ('APROVADA', 'REPROVADA', 'CANCELADA'))
;
ALTER TABLE T_EV_RECUPERACAO_LOGIN 
    ADD CONSTRAINT recuperacoes_login_PK PRIMARY KEY ( id_recuperacao ) ;

CREATE SEQUENCE SQ_EV_RECUPERACAO_LOGIN START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;

CREATE TABLE T_EV_RECURSO_REVISAO 
    ( 
     id_recurso   NUMBER (10)  NOT NULL , 
     id_validacao NUMBER (10)  NOT NULL , 
     id_usuario   NUMBER (10) , 
     id_revisor   NUMBER (10)  NOT NULL , 
     mt_recurso   VARCHAR2 (150) , 
     dc_recurso   VARCHAR2 (500) , 
     st_recurso   VARCHAR2 (20) DEFAULT 'ABERTO'  NOT NULL , 
     dt_abertura  DATE DEFAULT SYSDATE  NOT NULL , 
     dt_resposta  DATE , 
     rs_recurso   VARCHAR2 (20) , 
     ob_revisor   VARCHAR2 (500) 
    ) 
;

ALTER TABLE T_EV_RECURSO_REVISAO 
    ADD CONSTRAINT ck_rec_status 
    CHECK (st_recurso IN ('ABERTO', 'EM_ANALISE', 'RESPONDIDO', 'CANCELADO'))
;


ALTER TABLE T_EV_RECURSO_REVISAO 
    ADD CONSTRAINT ck_rec_result 
    CHECK (rs_recurso IS NULL OR rs_recurso IN ('APROVADO', 'REPROVADO', 'MANTIDO'))
;
ALTER TABLE T_EV_RECURSO_REVISAO 
    ADD CONSTRAINT recurso_revisao_PK PRIMARY KEY ( id_recurso ) ;
    
CREATE SEQUENCE SQ_EV_RECURSO_REVISAO START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;

CREATE TABLE T_EV_TIER 
    ( 
     id_tier           NUMBER (10)  NOT NULL , 
     nm_tier           VARCHAR2 (50)  NOT NULL , 
     dc_tier           VARCHAR2 (255) , 
     rd_tier           NUMBER (3)  NOT NULL , 
     us_ativos_minimos NUMBER (10) DEFAULT 0  NOT NULL , 
     pc_subida         NUMBER (5,2) , 
     pc_descida        NUMBER (5,2) , 
     lm_usuarios       NUMBER (10) , 
     st_tier           VARCHAR2 (20) DEFAULT 'ATIVO'  NOT NULL 
    ) 
;

ALTER TABLE T_EV_TIER 
    ADD CONSTRAINT ck_tier_status 
    CHECK (st_tier IN ('ATIVO', 'INATIVO'))
;


ALTER TABLE T_EV_TIER 
    ADD CONSTRAINT ck_tier_min 
    CHECK (us_ativos_minimos >= 0)
;
ALTER TABLE T_EV_TIER 
    ADD CONSTRAINT tiers_PK PRIMARY KEY ( id_tier ) ;

ALTER TABLE T_EV_TIER 
    ADD CONSTRAINT tiers_nome_tier_UN UNIQUE ( nm_tier ) ;

ALTER TABLE T_EV_TIER 
    ADD CONSTRAINT tiers_ordem_tier_UN UNIQUE ( rd_tier ) ;
    
CREATE SEQUENCE SQ_EV_TIER START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;

CREATE TABLE T_EV_USU_CON 
    ( 
     id_usuario           NUMBER (10)  NOT NULL , 
     id_conquista         NUMBER (10)  NOT NULL , 
     dt_desbloqueio       DATE , 
     pg_atual             NUMBER (10) DEFAULT 0  NOT NULL , 
     st_conquista_usuario VARCHAR2 (20) DEFAULT 'EM_ANDAMENTO'  NOT NULL , 
     vs_perfil            CHAR (1) DEFAULT 'S'  NOT NULL 
    ) 
;

ALTER TABLE T_EV_USU_CON 
    ADD CONSTRAINT ck_uc_status 
    CHECK (st_conquista_usuario IN ('EM_ANDAMENTO', 'DESBLOQUEADA', 'OCULTA'))
;


ALTER TABLE T_EV_USU_CON 
    ADD CONSTRAINT ck_uc_visivel 
    CHECK (vs_perfil IN ('S', 'N'))
;


ALTER TABLE T_EV_USU_CON 
    ADD CONSTRAINT ck_uc_prog 
    CHECK (pg_atual >= 0)
;
ALTER TABLE T_EV_USU_CON 
    ADD CONSTRAINT usuarios_conquistas_PK PRIMARY KEY ( id_usuario, id_conquista ) ;
    
CREATE SEQUENCE SQ_EV_USU_CON START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;

CREATE TABLE T_EV_USU_MIS 
    ( 
     id_usuario        NUMBER (10)  NOT NULL , 
     id_missao         NUMBER (10)  NOT NULL , 
     dt_inicio         DATE DEFAULT SYSDATE  NOT NULL , 
     dt_conclusao      DATE , 
     st_missao_usuario VARCHAR2 (20) DEFAULT 'EM_ANDAMENTO'  NOT NULL , 
     pg_missao_usuario NUMBER (5,2) DEFAULT 0  NOT NULL 
    ) 
;

ALTER TABLE T_EV_USU_MIS 
    ADD CONSTRAINT ck_um_status 
    CHECK (st_missao_usuario IN ('EM_ANDAMENTO', 'CONCLUIDA', 'EXPIRADA', 'CANCELADA'))
;


ALTER TABLE T_EV_USU_MIS 
    ADD CONSTRAINT ck_um_prog 
    CHECK (pg_missao_usuario BETWEEN 0 AND 100)
;
ALTER TABLE T_EV_USU_MIS 
    ADD CONSTRAINT usuario_missoes_PK PRIMARY KEY ( id_usuario, id_missao ) ;
    
CREATE SEQUENCE SQ_EV_USU_MIS START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;

CREATE TABLE T_EV_USUARIO 
    ( 
     id_usuario      NUMBER (10)  NOT NULL , 
     nm_usuario      VARCHAR2 (120)  NOT NULL , 
     em_usuario      VARCHAR2 (150)  NOT NULL , 
     sn_hash         VARCHAR2 (255)  NOT NULL , 
     dt_cadastro     DATE DEFAULT SYSDATE  NOT NULL , 
     st_usuario      VARCHAR2 (20) DEFAULT 'ATIVO'  NOT NULL , 
     tp_usuario      VARCHAR2 (20) DEFAULT 'COMUM'  NOT NULL , 
     xp_total        NUMBER (10) DEFAULT 0  NOT NULL , 
     pt_total        NUMBER (10) DEFAULT 0  NOT NULL , 
     dt_ultimo_login DATE , 
     at_usuario      CHAR (1) DEFAULT 'S'  NOT NULL , 
     id_tier         NUMBER (10)  NOT NULL , 
     id_nivel        NUMBER (10)  NOT NULL 
    ) 
;

ALTER TABLE T_EV_USUARIO 
    ADD CONSTRAINT ck_usr_status 
    CHECK (st_usuario IN ('ATIVO', 'INATIVO', 'BLOQUEADO'))
;


ALTER TABLE T_EV_USUARIO 
    ADD CONSTRAINT ck_usr_tipo 
    CHECK (tp_usuario IN ('COMUM', 'FUNCIONARIO', 'ADMIN'))
;


ALTER TABLE T_EV_USUARIO 
    ADD CONSTRAINT ck_usr_ativo 
    CHECK (at_usuario IN ('S', 'N'))
;


ALTER TABLE T_EV_USUARIO 
    ADD CONSTRAINT ck_usr_xp 
    CHECK (xp_total >= 0)
;


ALTER TABLE T_EV_USUARIO 
    ADD CONSTRAINT ck_usr_pts 
    CHECK (pt_total >= 0)
;
ALTER TABLE T_EV_USUARIO 
    ADD CONSTRAINT usuarios_PK PRIMARY KEY ( id_usuario ) ;

ALTER TABLE T_EV_USUARIO 
    ADD CONSTRAINT usuarios_UN UNIQUE ( em_usuario ) ;
    
CREATE SEQUENCE SQ_EV_USUARIO START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;

CREATE TABLE T_EV_VALIDACAO 
    ( 
     id_validacao    NUMBER (10)  NOT NULL , 
     id_envio        NUMBER (10)  NOT NULL , 
     tp_validacao    VARCHAR2 (30)  NOT NULL , 
     st_validacao    VARCHAR2 (20) DEFAULT 'PENDENTE'  NOT NULL , 
     ia_resultado    VARCHAR2 (500) , 
     sc_confianca_ia NUMBER (5,2) , 
     fr_detectada    CHAR (1) DEFAULT 'N'  NOT NULL , 
     cn_repetido     CHAR (1) DEFAULT 'N'  NOT NULL , 
     rt_confirmado   CHAR (1) , 
     cc_aprovada     CHAR (1) DEFAULT 'N'  NOT NULL , 
     dt_validacao    DATE DEFAULT SYSDATE  NOT NULL , 
     ob_validacao    VARCHAR2 (500) 
    ) 
;

ALTER TABLE T_EV_VALIDACAO 
    ADD CONSTRAINT ck_val_tipo 
    CHECK (tp_validacao IN ('IA', 'REGRA', 'HIBRIDA'))
;


ALTER TABLE T_EV_VALIDACAO 
    ADD CONSTRAINT ck_val_status 
    CHECK (st_validacao IN ('PENDENTE', 'APROVADA', 'REPROVADA', 'REVISAO'))
;


ALTER TABLE T_EV_VALIDACAO 
    ADD CONSTRAINT ck_val_score 
    CHECK (sc_confianca_ia IS NULL OR sc_confianca_ia BETWEEN 0 AND 100)
;


ALTER TABLE T_EV_VALIDACAO 
    ADD CONSTRAINT ck_val_fraude 
    CHECK (fr_detectada IN ('S', 'N'))
;


ALTER TABLE T_EV_VALIDACAO 
    ADD CONSTRAINT ck_val_rep 
    CHECK (cn_repetido IN ('S', 'N'))
;


ALTER TABLE T_EV_VALIDACAO 
    ADD CONSTRAINT ck_val_rosto 
    CHECK (rt_confirmado IS NULL OR rt_confirmado IN ('S', 'N'))
;


ALTER TABLE T_EV_VALIDACAO 
    ADD CONSTRAINT ck_val_coer 
    CHECK (cc_aprovada IN ('S', 'N'))
;
ALTER TABLE T_EV_VALIDACAO 
    ADD CONSTRAINT validacoes_PK PRIMARY KEY ( id_validacao ) ;
    
CREATE SEQUENCE SQ_EV_VALIDACAO START WITH 1 INCREMENT BY 1 NOCYCLE ORDER;

ALTER TABLE T_EV_ACA_SUS_MIS 
    ADD CONSTRAINT ACA_SUS_MIS_ACA_SUS_FK FOREIGN KEY 
    ( 
     id_acao
    ) 
    REFERENCES T_EV_ACAO_SUSTENTAVEIS 
    ( 
     id_acao
    ) 
;

ALTER TABLE T_EV_ACA_SUS_MIS 
    ADD CONSTRAINT ACA_SUS_MIS_MIS_FK FOREIGN KEY 
    ( 
     id_missao
    ) 
    REFERENCES T_EV_MISSAO 
    ( 
     id_missao
    ) 
;

ALTER TABLE T_EV_ACAO_SUSTENTAVEIS 
    ADD CONSTRAINT ACA_SUS_ODS_FK FOREIGN KEY 
    ( 
     id_ods
    ) 
    REFERENCES T_EV_ODS 
    ( 
     id_ods
    ) 
;

ALTER TABLE T_EV_CONTA_ENERGIA 
    ADD CONSTRAINT CON_ENE_REC_FK FOREIGN KEY 
    ( 
     id_recompensa
    ) 
    REFERENCES T_EV_RECOMPENSA 
    ( 
     id_recompensa
    ) 
;

ALTER TABLE T_EV_CONTA_ENERGIA 
    ADD CONSTRAINT CON_ENE_USU_FK FOREIGN KEY 
    ( 
     id_usuario
    ) 
    REFERENCES T_EV_USUARIO 
    ( 
     id_usuario
    ) 
;

ALTER TABLE T_EV_CONQUISTA 
    ADD CONSTRAINT CON_NIV_FK FOREIGN KEY 
    ( 
     id_nivel
    ) 
    REFERENCES T_EV_NIVEL 
    ( 
     id_nivel
    ) 
;

ALTER TABLE T_EV_ENVIO_ACAO 
    ADD CONSTRAINT ENV_ACA_ACA_SUS_FK FOREIGN KEY 
    ( 
     T_EV_ACAO_SUSTENTAVEIS_id_acao
    ) 
    REFERENCES T_EV_ACAO_SUSTENTAVEIS 
    ( 
     id_acao
    ) 
;

ALTER TABLE T_EV_ENV_ACA_MIS 
    ADD CONSTRAINT ENV_ACA_MIS_ENV_ACA_FK FOREIGN KEY 
    ( 
     id_envio
    ) 
    REFERENCES T_EV_ENVIO_ACAO 
    ( 
     id_envio
    ) 
;

ALTER TABLE T_EV_ENV_ACA_MIS 
    ADD CONSTRAINT ENV_ACA_MIS_MIS_FK FOREIGN KEY 
    ( 
     id_missao
    ) 
    REFERENCES T_EV_MISSAO 
    ( 
     id_missao
    ) 
;

ALTER TABLE T_EV_ENVIO_ACAO 
    ADD CONSTRAINT ENV_ACA_USU_FK FOREIGN KEY 
    ( 
     T_EV_USUARIO_id_usuario
    ) 
    REFERENCES T_EV_USUARIO 
    ( 
     id_usuario
    ) 
;

ALTER TABLE T_EV_INTERACAO 
    ADD CONSTRAINT INT_USU_FK FOREIGN KEY 
    ( 
     id_usuario
    ) 
    REFERENCES T_EV_USUARIO 
    ( 
     id_usuario
    ) 
;

ALTER TABLE T_EV_LOGIN_DIARIO 
    ADD CONSTRAINT LOG_DIA_USU_FK FOREIGN KEY 
    ( 
     id_usuario
    ) 
    REFERENCES T_EV_USUARIO 
    ( 
     id_usuario
    ) 
;

ALTER TABLE T_EV_MISSAO 
    ADD CONSTRAINT MIS_NIV_FK FOREIGN KEY 
    ( 
     id_nivel
    ) 
    REFERENCES T_EV_NIVEL 
    ( 
     id_nivel
    ) 
;

ALTER TABLE T_EV_MISSAO 
    ADD CONSTRAINT MIS_TIE_FK FOREIGN KEY 
    ( 
     id_tier
    ) 
    REFERENCES T_EV_TIER 
    ( 
     id_tier
    ) 
;

ALTER TABLE T_EV_ODS_MIS 
    ADD CONSTRAINT ODS_MIS_MIS_FK FOREIGN KEY 
    ( 
     id_missao
    ) 
    REFERENCES T_EV_MISSAO 
    ( 
     id_missao
    ) 
;

ALTER TABLE T_EV_ODS_MIS 
    ADD CONSTRAINT ODS_MIS_ODS_FK FOREIGN KEY 
    ( 
     id_ods
    ) 
    REFERENCES T_EV_ODS 
    ( 
     id_ods
    ) 
;

ALTER TABLE T_EV_PONTUACAO 
    ADD CONSTRAINT PON_ENV_ACA_FK FOREIGN KEY 
    ( 
     id_envio
    ) 
    REFERENCES T_EV_ENVIO_ACAO 
    ( 
     id_envio
    ) 
;

ALTER TABLE T_EV_PONTUACAO 
    ADD CONSTRAINT PON_INT_FK FOREIGN KEY 
    ( 
     id_interacao
    ) 
    REFERENCES T_EV_INTERACAO 
    ( 
     id_interacao
    ) 
;

ALTER TABLE T_EV_PONTUACAO 
    ADD CONSTRAINT PON_LOG_DIA_FK FOREIGN KEY 
    ( 
     id_login
    ) 
    REFERENCES T_EV_LOGIN_DIARIO 
    ( 
     id_login
    ) 
;

ALTER TABLE T_EV_PONTUACAO 
    ADD CONSTRAINT PON_MIS_FK FOREIGN KEY 
    ( 
     id_missao
    ) 
    REFERENCES T_EV_MISSAO 
    ( 
     id_missao
    ) 
;

ALTER TABLE T_EV_PONTUACAO 
    ADD CONSTRAINT PON_NIV_FK FOREIGN KEY 
    ( 
     id_nivel
    ) 
    REFERENCES T_EV_NIVEL 
    ( 
     id_nivel
    ) 
;

ALTER TABLE T_EV_PONTUACAO 
    ADD CONSTRAINT PON_REC_REV_FK FOREIGN KEY 
    ( 
     id_recurso
    ) 
    REFERENCES T_EV_RECURSO_REVISAO 
    ( 
     id_recurso
    ) 
;

ALTER TABLE T_EV_PONTUACAO 
    ADD CONSTRAINT PON_USU_FK FOREIGN KEY 
    ( 
     id_usuario
    ) 
    REFERENCES T_EV_USUARIO 
    ( 
     id_usuario
    ) 
;

ALTER TABLE T_EV_PONTUACAO 
    ADD CONSTRAINT PON_VAL_FK FOREIGN KEY 
    ( 
     id_validacao
    ) 
    REFERENCES T_EV_VALIDACAO 
    ( 
     id_validacao
    ) 
;

ALTER TABLE T_EV_RANKING 
    ADD CONSTRAINT RAN_TIE_FK FOREIGN KEY 
    ( 
     id_tier
    ) 
    REFERENCES T_EV_TIER 
    ( 
     id_tier
    ) 
;

ALTER TABLE T_EV_RANKING 
    ADD CONSTRAINT RAN_USU_FK FOREIGN KEY 
    ( 
     id_usuario
    ) 
    REFERENCES T_EV_USUARIO 
    ( 
     id_usuario
    ) 
;

ALTER TABLE T_EV_RECUPERACAO_LOGIN 
    ADD CONSTRAINT REC_LOG_LOG_DIA_FK FOREIGN KEY 
    ( 
     id_login
    ) 
    REFERENCES T_EV_LOGIN_DIARIO 
    ( 
     id_login
    ) 
;

ALTER TABLE T_EV_RECUPERACAO_LOGIN 
    ADD CONSTRAINT REC_LOG_USU_FK FOREIGN KEY 
    ( 
     id_usuario
    ) 
    REFERENCES T_EV_USUARIO 
    ( 
     id_usuario
    ) 
;

ALTER TABLE T_EV_RECOMPENSA 
    ADD CONSTRAINT REC_RAN_FK FOREIGN KEY 
    ( 
     id_ranking
    ) 
    REFERENCES T_EV_RANKING 
    ( 
     id_ranking
    ) 
;

ALTER TABLE T_EV_RECURSO_REVISAO 
    ADD CONSTRAINT REC_REV_REV_FK FOREIGN KEY 
    ( 
     id_revisor
    ) 
    REFERENCES T_EV_USUARIO 
    ( 
     id_usuario
    ) 
;

ALTER TABLE T_EV_RECURSO_REVISAO 
    ADD CONSTRAINT REC_REV_USU_FK FOREIGN KEY 
    ( 
     id_usuario
    ) 
    REFERENCES T_EV_USUARIO 
    ( 
     id_usuario
    ) 
;

ALTER TABLE T_EV_RECURSO_REVISAO 
    ADD CONSTRAINT REC_REV_VAL_FK FOREIGN KEY 
    ( 
     id_validacao
    ) 
    REFERENCES T_EV_VALIDACAO 
    ( 
     id_validacao
    ) 
;

ALTER TABLE T_EV_RECOMPENSA 
    ADD CONSTRAINT REC_TIE_FK FOREIGN KEY 
    ( 
     id_tier
    ) 
    REFERENCES T_EV_TIER 
    ( 
     id_tier
    ) 
;

ALTER TABLE T_EV_RECOMPENSA 
    ADD CONSTRAINT REC_USU_FK FOREIGN KEY 
    ( 
     id_usuario
    ) 
    REFERENCES T_EV_USUARIO 
    ( 
     id_usuario
    ) 
;

ALTER TABLE T_EV_USU_CON 
    ADD CONSTRAINT USU_CON_CON_FK FOREIGN KEY 
    ( 
     id_conquista
    ) 
    REFERENCES T_EV_CONQUISTA 
    ( 
     id_conquista
    ) 
;

ALTER TABLE T_EV_USU_CON 
    ADD CONSTRAINT USU_CON_USU_FK FOREIGN KEY 
    ( 
     id_usuario
    ) 
    REFERENCES T_EV_USUARIO 
    ( 
     id_usuario
    ) 
;

ALTER TABLE T_EV_USU_MIS 
    ADD CONSTRAINT USU_MIS_MIS_FK FOREIGN KEY 
    ( 
     id_missao
    ) 
    REFERENCES T_EV_MISSAO 
    ( 
     id_missao
    ) 
;

ALTER TABLE T_EV_USU_MIS 
    ADD CONSTRAINT USU_MIS_USU_FK FOREIGN KEY 
    ( 
     id_usuario
    ) 
    REFERENCES T_EV_USUARIO 
    ( 
     id_usuario
    ) 
;

ALTER TABLE T_EV_USUARIO 
    ADD CONSTRAINT USU_NIV_FK FOREIGN KEY 
    ( 
     id_nivel
    ) 
    REFERENCES T_EV_NIVEL 
    ( 
     id_nivel
    ) 
;

ALTER TABLE T_EV_USUARIO 
    ADD CONSTRAINT USU_TIE_FK FOREIGN KEY 
    ( 
     id_tier
    ) 
    REFERENCES T_EV_TIER 
    ( 
     id_tier
    ) 
;

ALTER TABLE T_EV_VALIDACAO 
    ADD CONSTRAINT VAL_ENV_ACA_FK FOREIGN KEY 
    ( 
     id_envio
    ) 
    REFERENCES T_EV_ENVIO_ACAO 
    ( 
     id_envio
    ) 
;