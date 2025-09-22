-- criando usuários
INSERT INTO usuario (id, nome, cpf, email, cargo, login, senha)
VALUES 
(1, 'Amanda', '11111111111', 'amanda@email.com', 'Desenvolvedora', 'amandaDev', '123456'),
(2, 'Guilherme', '22222222222', 'guilherme@email.com', 'Analista', 'guiAnalyst', '654321'),
(3, 'Hamilton', '33333333333', 'hamilton@email.com', 'Tester', 'hamTester', 'senha789');

-- criando equipes
INSERT INTO equipe (id, nome) VALUES
(1, 'Equipe Backend');

-- criando projetos
INSERT INTO projeto (id, nome, descricao) VALUES
(1, 'Sistema Web', 'Projeto para controle de usuários, equipes e projetos');

-- usuários com equipe
INSERT INTO usuario_equipes (usuario_id, equipe_id) VALUES
(1, 1),
(2, 1),
(3, 1);

-- usuários com projeto
INSERT INTO usuario_projetos (usuario_id, projeto_id) VALUES
(1, 1),
(2, 1),
(3, 1);
