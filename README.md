# CarRevision
Projet Android réalisé dans le cadre du cours 644-1 "Développement mobile et cloud" par Arthur Avez et Zacharie Renna.

<h1>Description</h1>
Développée à l'aide d'Android Studio et de Github pour le VCS, l'application mobile "Car Revisions" a pour but d'être utilisée au sein d'un garage pour gérer des révisions de voitures.
Le principe de cette application est d'afficher la liste complète de toutes les révisions et de toutes les voitures traitées par le garage.
Afin d'ajouter/modifier/supprimer une voiture, il est nécessaire d'être authentifié en tant que technicien.
Il en va de même pour les révisions, au détail près qu'elles ne peuvent être ajoutées/supprimées/modifiées uniquement par le technicien auquel elle a été attribuée ou par un technicien administrateur (Fred ou Shaggy).
L'application est disponible en deux langues (français et anglais) et permet également à un technicien lors de l'authentification de rester connecté pour la prochaine ouverture de l'application.
N'importe qui peut s'enregister en tant que technicien non-administrateur, nous sommes partis du principe que l'application ne serait disponible à l'installation qu'en interne.


<h1>Fonctionnalités</h1>
<ul>
<li>Affichage et recherche dans la liste des voitures</li>
<li>Affichage et recherche dans la liste des révisions</li>
<li>Affichage/édition/suppression d'une voiture</li>
<li>Affichage/édition/suppression d'une révision</li>
<li>Création d'une nouvelle voiture depuis la création de révision</li>
<li>Authentification sécurisée avec stockage des mots de passes hashés en utilisant l'algorithme "PBKDF2WithHmacSHA1"</li>
<li>Possibilité de rester connecté pour la prochaine ouverture de l'application</li>
<li>Enregistrement en tant que technicien</li>
<li>Traductions en deux langues (français et anglais) et support des formats de date correspondants</li>
</ul>

<h1>Comptes démo</h1>
<ul>
<li>fred.jones@sbg.com : "MysteryMachine78" [ADMIN]</li>
<li>shaggy.rogers@sbg.com : "Zoinks!" [ADMIN]</li>
<li>vera.dinkley@sbg.com : "Jinkies!"</li>
<li>daphne.blake@sbg.com : "Jeepers!"</li>
<li>scooby.doo@sbg.com : "Ruh-roh-RAGGY"</li>
</ul>
