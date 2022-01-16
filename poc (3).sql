-- phpMyAdmin SQL Dump
-- version 4.5.4.1
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Dim 16 Janvier 2022 à 17:40
-- Version du serveur :  5.7.11
-- Version de PHP :  5.6.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `poc`
--

-- --------------------------------------------------------

--
-- Structure de la table `projects`
--

CREATE TABLE `projects` (
  `id_project` bigint(20) NOT NULL,
  `project_name` varchar(255) DEFAULT NULL,
  `manager_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `projects`
--

INSERT INTO `projects` (`id_project`, `project_name`, `manager_id`) VALUES
(1, 'kanban1', 3),
(7, 'gfddfgdfg', 3),
(8, 'sqdqsdxwcwxc', 3),
(12, 'wxcqsd', 3);

-- --------------------------------------------------------

--
-- Structure de la table `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `name` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_MANAGER'),
(3, 'ROLE_ADMIN');

-- --------------------------------------------------------

--
-- Structure de la table `times`
--

CREATE TABLE `times` (
  `id_time` bigint(20) NOT NULL,
  `date_saisie` datetime DEFAULT NULL,
  `date_travail` datetime DEFAULT NULL,
  `nb_hours` varchar(255) DEFAULT NULL,
  `proj_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `times`
--

INSERT INTO `times` (`id_time`, `date_saisie`, `date_travail`, `nb_hours`, `proj_id`, `user_id`) VALUES
(8, '2022-01-01 15:50:21', '2022-01-19 00:00:00', '09:00', 1, 1),
(38, '2022-01-04 17:39:19', '2022-01-08 00:00:00', '17:39', 8, 3),
(40, '2022-01-05 09:17:45', '2021-12-15 00:00:00', '09:17', 7, 3),
(41, '2022-01-05 09:17:47', '2021-10-11 00:00:00', '09:17', 7, 3),
(43, '2022-01-16 14:46:42', '2022-01-20 00:00:00', '19:46', 1, 3);

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `password` varchar(120) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `username` varchar(25) DEFAULT NULL,
  `manager` bigint(20) DEFAULT NULL,
  `latest_cr` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `users`
--

INSERT INTO `users` (`id`, `email`, `nom`, `password`, `prenom`, `username`, `manager`, `latest_cr`) VALUES
(1, 'User@mail.com', 'User_nom', '$2a$10$.xADiUqwxUtRz19zWdXwW.sU5GKzGja3cY1avQuVgg11vCqVbrW1W', 'User_prenom', 'TestUser', 2, NULL),
(2, 'Manager@mail.com', 'Manager_nom', '$2a$10$oN2HihIJVnICp9hPglNBAOAOtcUhh6MIPR8DdL1uZ.PYs7PA6a.hC', 'Manager_prenom', 'TestManager', 3, NULL),
(3, 'Admin@mail.com', 'Admin_nom', '$2a$10$TAFMYRbZVYJKQ.jwCfNEGO2pjCFk.GSU5EeQ7ISObcASDeq6b6/iy', 'Admin_prenom', 'TestAdmin', NULL, '2022-01-16 00:00:00'),
(4, 'test@hghgjg', 'User2', '$2a$10$5XFv65n49w43Omo8K9mTPerMqmwI.LEPsi9JhaNBzYYktDXj9ZAjS', 'Usser', 'TestUser2', 2, NULL),
(5, 'testmod@test', 'mod2', '$2a$10$06JX0IcIjq8KGIb5/kn/puRn1WK.g9sslKLquZsOPC3iQZ8bqHL1u', 'mod2', 'mod2', 3, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `user_roles`
--

CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `user_roles`
--

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(2, 2),
(3, 2),
(5, 2),
(3, 3),
(5, 3);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `projects`
--
ALTER TABLE `projects`
  ADD PRIMARY KEY (`id_project`),
  ADD KEY `FKurl8wb4qjly2c5xwdcpetuxs` (`manager_id`);

--
-- Index pour la table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `times`
--
ALTER TABLE `times`
  ADD PRIMARY KEY (`id_time`),
  ADD KEY `FK76mphqae6v2x62xf9lv4j46du` (`proj_id`),
  ADD KEY `FKcnw6pf7rungfiyjq2kkuvdrik` (`user_id`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  ADD UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  ADD KEY `FKr81suo58hnjkrsytcjfnfjn0t` (`manager`);

--
-- Index pour la table `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `projects`
--
ALTER TABLE `projects`
  MODIFY `id_project` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT pour la table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT pour la table `times`
--
ALTER TABLE `times`
  MODIFY `id_time` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;
--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `projects`
--
ALTER TABLE `projects`
  ADD CONSTRAINT `FKurl8wb4qjly2c5xwdcpetuxs` FOREIGN KEY (`manager_id`) REFERENCES `users` (`id`);

--
-- Contraintes pour la table `times`
--
ALTER TABLE `times`
  ADD CONSTRAINT `FK76mphqae6v2x62xf9lv4j46du` FOREIGN KEY (`proj_id`) REFERENCES `projects` (`id_project`),
  ADD CONSTRAINT `FKcnw6pf7rungfiyjq2kkuvdrik` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Contraintes pour la table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `FKr81suo58hnjkrsytcjfnfjn0t` FOREIGN KEY (`manager`) REFERENCES `users` (`id`);

--
-- Contraintes pour la table `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  ADD CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
