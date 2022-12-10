# CarRevision
Projet Android réalisé dans le cadre du cours 644-1 "Développement mobile et cloud" par Arthur Avez et Zacharie Renna.

<h1>Description</h1>
Développée à l'aide d'Android Studio et de Github pour le VCS, l'application mobile "Car Revisions" a pour but d'être utilisée au sein d'un garage pour gérer des révisions de voitures.
Le principe de cette application est d'afficher la liste complète de toutes les révisions et de toutes les voitures traitées par le garage.
Afin d'ajouter/modifier/supprimer une voiture, il est nécessaire d'être authentifié en tant que technicien.
Il en va de même pour les révisions, au détail près qu'elles ne peuvent être ajoutées/supprimées/modifiées uniquement par le technicien dont il est responsable ou par un technicien administrateur (Fred ou Shaggy).
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

<h1>Règles de sécurité Firebase</h1>

```yaml
{
  "rules": {
    ".read": true,
    ".write": false,
    "cars": {
      ".write": "auth !== null"
    },
    "revisions": {
      ".write": "auth !== null"
    },
    "technicians": {
      "$uid": {
        ".read": "auth !== null && auth.uid === $uid",
        ".write": "auth !== null && auth.uid === $uid"
      }
    }
  }
}
```

<h1>Json</h1>

```yaml
{
  "brands": [
    null,
    {
      "brand": "Chevrolet"
    },
    {
      "brand": "Dodge"
    },
    {
      "brand": "Hummer"
    },
    {
      "brand": "Lincoln"
    },
    {
      "brand": "Mercury"
    },
    {
      "brand": "Subaru"
    }
  ],
  "cantons": [
    null,
    {
      "abbreviation": "ZH",
      "canton": "Zürich"
    },
    {
      "abbreviation": "BE",
      "canton": "Berne"
    },
    {
      "abbreviation": "LU",
      "canton": "Lucerne"
    },
    {
      "abbreviation": "UR",
      "canton": "Uri"
    },
    {
      "abbreviation": "SZ",
      "canton": "Schwyz"
    },
    {
      "abbreviation": "OW",
      "canton": "Obwald"
    },
    {
      "abbreviation": "NW",
      "canton": "Nidwald"
    },
    {
      "abbreviation": "GL",
      "canton": "Glaris"
    },
    {
      "abbreviation": "ZG",
      "canton": "Zoug"
    },
    {
      "abbreviation": "FB",
      "canton": "Fribourg"
    },
    {
      "abbreviation": "SO",
      "canton": "Soleure"
    },
    {
      "abbreviation": "BS",
      "canton": "Bâle-Ville"
    },
    {
      "abbreviation": "BL",
      "canton": "Bâle-Campagne"
    },
    {
      "abbreviation": "SH",
      "canton": "Schaffhouse"
    },
    {
      "abbreviation": "AR",
      "canton": "Appenzell Rh.-Ext."
    },
    {
      "abbreviation": "AI",
      "canton": "Appenzell Rh.-Int."
    },
    {
      "abbreviation": "SG",
      "canton": "Saint-Gall"
    },
    {
      "abbreviation": "GR",
      "canton": "Grisons"
    },
    {
      "abbreviation": "AG",
      "canton": "Argovie"
    },
    {
      "abbreviation": "TG",
      "canton": "Thurgovie"
    },
    {
      "abbreviation": "TI",
      "canton": "Tessin"
    },
    {
      "abbreviation": "VD",
      "canton": "Vaud"
    },
    {
      "abbreviation": "VS",
      "canton": "Valais"
    },
    {
      "abbreviation": "NE",
      "canton": "Neuchâtel"
    },
    {
      "abbreviation": "GE",
      "canton": "Genève"
    },
    {
      "abbreviation": "JU",
      "canton": "Jura"
    }
  ],
  "cars": {
    "-NIv_tkBBv0F7KM6LrT8": {
      "kilometers": 1,
      "modelId": 1,
      "plate": "AG123",
      "year": 1640991600000
    },
    "-NIvhTKW3cV3Z5WIZgdy": {
      "kilometers": 69000,
      "modelId": 32,
      "plate": "AG111111",
      "year": 757382400000
    },
    "-NIvhfN7sWHLjA94jags": {
      "kilometers": 69000,
      "modelId": 1,
      "plate": "AG1",
      "year": 1640995200000
    },
    "-NIvhwnl5baht-6LuDMB": {
      "kilometers": 50000,
      "modelId": 24,
      "plate": "VS890890",
      "year": 1640995200000
    }
  },
  "models": [
    null,
    {
      "brandId": 1,
      "model": "Astro Cargo"
    },
    {
      "brandId": 1,
      "model": "Astro Passenger"
    },
    {
      "brandId": 1,
      "model": "Avalanche"
    },
    {
      "brandId": 1,
      "model": "Aveo"
    },
    {
      "brandId": 1,
      "model": "Blazer"
    },
    {
      "brandId": 1,
      "model": "Camaro"
    },
    {
      "brandId": 1,
      "model": "Cavalier"
    },
    {
      "brandId": 1,
      "model": "Classic"
    },
    {
      "brandId": 1,
      "model": "Corvette"
    },
    {
      "brandId": 1,
      "model": "Equinox"
    },
    {
      "brandId": 1,
      "model": "Impala"
    },
    {
      "brandId": 1,
      "model": "Malibu"
    },
    {
      "brandId": 1,
      "model": "Prizm"
    },
    {
      "brandId": 1,
      "model": "Silverado"
    },
    {
      "brandId": 1,
      "model": "Suburban"
    },
    {
      "brandId": 1,
      "model": "Volt"
    },
    {
      "brandId": 2,
      "model": "Avenger"
    },
    {
      "brandId": 2,
      "model": "Caliber"
    },
    {
      "brandId": 2,
      "model": "Colt"
    },
    {
      "brandId": 2,
      "model": "Dart"
    },
    {
      "brandId": 2,
      "model": "Intrepid"
    },
    {
      "brandId": 2,
      "model": "Journey"
    },
    {
      "brandId": 2,
      "model": "Stratus"
    },
    {
      "brandId": 2,
      "model": "Viper"
    },
    {
      "brandId": 3,
      "model": "H1"
    },
    {
      "brandId": 3,
      "model": "H2"
    },
    {
      "brandId": 3,
      "model": "H3"
    },
    {
      "brandId": 3,
      "model": "H3T"
    },
    {
      "brandId": 4,
      "model": "Aviator"
    },
    {
      "brandId": 4,
      "model": "Blackwood"
    },
    {
      "brandId": 4,
      "model": "Continental"
    },
    {
      "brandId": 4,
      "model": "Corsair"
    },
    {
      "brandId": 4,
      "model": "LS"
    },
    {
      "brandId": 4,
      "model": "MKC"
    },
    {
      "brandId": 4,
      "model": "MKS"
    },
    {
      "brandId": 4,
      "model": "Nautilus"
    },
    {
      "brandId": 4,
      "model": "Navigator"
    },
    {
      "brandId": 4,
      "model": "Town Car"
    },
    {
      "brandId": 4,
      "model": "Zephyr"
    },
    {
      "brandId": 5,
      "model": "Capri"
    },
    {
      "brandId": 5,
      "model": "Cougar"
    },
    {
      "brandId": 5,
      "model": "Grand Marquis"
    },
    {
      "brandId": 5,
      "model": "Mariner"
    },
    {
      "brandId": 5,
      "model": "Milan"
    },
    {
      "brandId": 5,
      "model": "Montego"
    },
    {
      "brandId": 5,
      "model": "Monterey"
    },
    {
      "brandId": 5,
      "model": "Mountaineer"
    },
    {
      "brandId": 5,
      "model": "Mystique"
    },
    {
      "brandId": 5,
      "model": "Sable"
    },
    {
      "brandId": 5,
      "model": "Topaz"
    },
    {
      "brandId": 5,
      "model": "Tracer"
    },
    {
      "brandId": 5,
      "model": "Villager"
    },
    {
      "brandId": 6,
      "model": "Ascent"
    },
    {
      "brandId": 6,
      "model": "BRZ"
    },
    {
      "brandId": 6,
      "model": "Baja"
    },
    {
      "brandId": 6,
      "model": "Crosstrek"
    },
    {
      "brandId": 6,
      "model": "Forester"
    },
    {
      "brandId": 6,
      "model": "Impreza"
    },
    {
      "brandId": 6,
      "model": "Justy"
    },
    {
      "brandId": 6,
      "model": "Legacy"
    },
    {
      "brandId": 6,
      "model": "Loyale"
    },
    {
      "brandId": 6,
      "model": "Outback"
    },
    {
      "brandId": 6,
      "model": "Tribeca"
    },
    {
      "brandId": 6,
      "model": "WRX"
    },
    {
      "brandId": 6,
      "model": "XV Crosstrek"
    }
  ],
  "revisions": {
    "-NIva-QKFIcGX7FyvacZ": {
      "carId": "-NIv_tkBBv0F7KM6LrT8",
      "end": 1670880120000,
      "start": 1670880120000,
      "status": 0,
      "technicianId": "e6gNzujpH0cnz2MNltxbniHhG4u2"
    },
    "-NIvhaKJ3OSCjK7VRYy-": {
      "carId": "-NIvhTKW3cV3Z5WIZgdy",
      "end": 1577872800000,
      "start": 1577865600000,
      "status": 2,
      "technicianId": "e6gNzujpH0cnz2MNltxbniHhG4u2"
    }
  },
  "technicians": {
    "0goayOkHe0fEud17yq1DZChDsxs2": {
      "admin": false,
      "firstname": "Daphne",
      "lastname": "Blake",
      "title": "Mme."
    },
    "FPdMTP1KqEcAopSdBmCWrxaLQqY2": {
      "admin": true,
      "firstname": "Shaggy",
      "lastname": "Rogers",
      "title": "M."
    },
    "e6gNzujpH0cnz2MNltxbniHhG4u2": {
      "admin": false,
      "firstname": "Scooby",
      "lastname": "Doo",
      "title": "M."
    },
    "eVLDIr9NBNWWlzdh0U032F4ZbhI2": {
      "admin": true,
      "firstname": "Fred",
      "lastname": "Jones",
      "title": "M."
    },
    "jXJhVkRNqbYHfMkN9Uzq24c3Xi92": {
      "admin": false,
      "firstname": "Vera",
      "lastname": "Dinkley",
      "title": "Mme."
    }
  }
}
```
