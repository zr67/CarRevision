package com.example.carrevision.database;

import android.os.AsyncTask;
import android.util.Log;

import com.example.carrevision.database.entity.BrandEntity;
import com.example.carrevision.database.entity.CantonEntity;
import com.example.carrevision.database.entity.CarEntity;
import com.example.carrevision.database.entity.ModelEntity;
import com.example.carrevision.database.entity.RevisionEntity;
import com.example.carrevision.database.entity.TechnicianEntity;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Database initializer class
 */
public class DBInitializer {
    public static final String TAG = "DBInitializer";

    /**
     * Populates the database with the base data
     * @param db Database to populate
     */
    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting base data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    /**
     * Adds a brand to the database
     * @param db Database
     * @param brand Brand's name
     */
    private static void addBrand(final AppDatabase db, final String brand) {
        BrandEntity brandEntity = new BrandEntity(brand);
        db.brandDao().insert(brandEntity);
    }

    /**
     * Adds a model to the database
     * @param db Database
     * @param brandId Brand's identifier
     * @param model Model's name
     */
    private static void addModel(final AppDatabase db, final int brandId, final String model) {
        ModelEntity modelEntity = new ModelEntity(brandId, model);
        db.modelDao().insert(modelEntity);
    }

    /**
     * Adds a canton to the database
     * @param db Database
     * @param canton Canton's name
     * @param abbreviation Canton's abbreviation
     */
    private static void addCanton(final AppDatabase db, final String canton, final String abbreviation) {
        CantonEntity cantonEntity = new CantonEntity(canton, abbreviation);
        db.cantonDao().insert(cantonEntity);
    }

    /**
     * Adds a car to the database
     * @param db Database
     * @param modelId Model's identifier
     * @param plate Car's plate number
     * @param year Car's issuance year
     * @param kilometers Car's mileage
     */
    private static void addCar(final AppDatabase db, final int modelId, final String plate, final Date year, final int kilometers) {
        CarEntity carEntity = new CarEntity(modelId, plate, year, kilometers);
        db.carDao().insert(carEntity);
    }

    /**
     * Adds a technician to the database
     * @param db Database
     * @param title Technician's title
     * @param firstname Technician's first name
     * @param lastname Technician's last name
     * @param email Technician's email
     */
    private static void addTechnician(final AppDatabase db, final String title, final String firstname, final String lastname, final String email) {
        TechnicianEntity technicianEntity = new TechnicianEntity(title, firstname, lastname, email);
        db.technicianDao().insert(technicianEntity);
    }

    /**
     * Adds a revision to the database
     * @param db Database
     * @param technicianId Technician's identifier
     * @param carId Car's identifier
     * @param start Revision's start date and time
     * @param end Revision's end date and time
     * @param status Revision's status
     */
    private static void addRevision(final AppDatabase db, final int technicianId, final int carId, final Date start, final Date end, final String status) {
        RevisionEntity revisionEntity = new RevisionEntity(technicianId, carId, start, end, status);
        db.revisionDao().insert(revisionEntity);
    }

    /**
     * Adds all base brands to the database
     * @param db Database
     */
    private static void addBrands(AppDatabase db) {
        db.brandDao().deleteAll();

        addBrand(db, "Chevrolet");
        addBrand(db, "Dodge");
        addBrand(db, "Hummer");
        addBrand(db, "Lincoln");
        addBrand(db, "Mercury");
        addBrand(db, "Subaru");
    }

    /**
     * Adds all base models to the database
     * @param db Database
     */
    private static void addModels(AppDatabase db) {
        db.modelDao().deleteAll();

        addModel(db, 1, "Astro Cargo");
        addModel(db, 1, "Astro Passenger");
        addModel(db, 1, "Avalanche");
        addModel(db, 1, "Aveo");
        addModel(db, 1, "Blazer");
        addModel(db, 1, "Camaro");
        addModel(db, 1, "Cavalier");
        addModel(db, 1, "Classic");
        addModel(db, 1, "Corvette");
        addModel(db, 1, "Equinox");
        addModel(db, 1, "Impala");
        addModel(db, 1, "Malibu");
        addModel(db, 1, "Prizm");
        addModel(db, 1, "Silverado");
        addModel(db, 1, "Suburban");
        addModel(db, 1, "Volt");
        addModel(db, 2, "Avenger");
        addModel(db, 2, "Caliber");
        addModel(db, 2, "Colt");
        addModel(db, 2, "Dart");
        addModel(db, 2, "Intrepid");
        addModel(db, 2, "Journey");
        addModel(db, 2, "Stratus");
        addModel(db, 2, "Viper");
        addModel(db, 3, "H1");
        addModel(db, 3, "H2");
        addModel(db, 3, "H3");
        addModel(db, 3, "H3T");
        addModel(db, 4, "Aviator");
        addModel(db, 4, "Blackwood");
        addModel(db, 4, "Continental");
        addModel(db, 4, "Corsair");
        addModel(db, 4, "LS");
        addModel(db, 4, "MKC");
        addModel(db, 4, "MKS");
        addModel(db, 4, "Nautilus");
        addModel(db, 4, "Navigator");
        addModel(db, 4, "Town Car");
        addModel(db, 4, "Zephyr");
        addModel(db, 5, "Capri");
        addModel(db, 5, "Cougar");
        addModel(db, 5, "Grand Marquis");
        addModel(db, 5, "Mariner");
        addModel(db, 5, "Milan");
        addModel(db, 5, "Montego");
        addModel(db, 5, "Monterey");
        addModel(db, 5, "Moutaineer");
        addModel(db, 5, "Mystique");
        addModel(db, 5, "Sable");
        addModel(db, 5, "Topaz");
        addModel(db, 5, "Tracer");
        addModel(db, 5, "Villager");
        addModel(db, 6, "Ascent");
        addModel(db, 6, "BRZ");
        addModel(db, 6, "Baja");
        addModel(db, 6, "Crosstrek");
        addModel(db, 6, "Forester");
        addModel(db, 6, "Impreza");
        addModel(db, 6, "Justy");
        addModel(db, 6, "Legacy");
        addModel(db, 6, "Loyale");
        addModel(db, 6, "Outback");
        addModel(db, 6, "Tribeca");
        addModel(db, 6, "WRX");
        addModel(db, 6, "XV Crosstrek");
    }

    /**
     * Adds all cantons to the database
     * @param db Database
     */
    private static void addCantons(AppDatabase db) {
        db.cantonDao().deleteAll();

        addCanton(db, "Zürich", "ZH");
        addCanton(db, "Berne", "BE");
        addCanton(db, "Lucerne", "LU");
        addCanton(db, "Uri", "UR");
        addCanton(db, "Schwyz", "SZ");
        addCanton(db, "Obwald", "OW");
        addCanton(db, "Nidwald", "NW");
        addCanton(db, "Glaris", "GL");
        addCanton(db, "Zoug", "ZG");
        addCanton(db, "Fribourg", "FR");
        addCanton(db, "Soleure", "SO");
        addCanton(db, "Bâle-Ville", "BS");
        addCanton(db, "Bâle-Campagne", "BL");
        addCanton(db, "Schaffhouse", "SH");
        addCanton(db, "Appenzell Rh.-Ext.", "AR");
        addCanton(db, "Appenzell Rh.-Int.", "AI");
        addCanton(db, "Saint-Gall", "SG");
        addCanton(db, "Grisons", "GR");
        addCanton(db, "Argovie", "AG");
        addCanton(db, "Thurgovie", "TG");
        addCanton(db, "Tessin", "TI");
        addCanton(db, "Vaud", "VD");
        addCanton(db, "Valais", "VS");
        addCanton(db, "Neuchâtel", "NE");
        addCanton(db, "Genève", "GE");
        addCanton(db, "Jura", "JU");
    }

    /**
     * Adds all base cars to the database
     * @param db Database
     */
    private static void addCars(AppDatabase db) {
        db.carDao().deleteAll();

        addCar(db, 11, "VS470607", new GregorianCalendar(1967, Calendar.JANUARY, 1).getTime(), 60000);
        addCar(db, 41, "VS275170", new GregorianCalendar(2015, Calendar.JANUARY, 1).getTime(), 185147);
        addCar(db, 25, "SZ58400", new GregorianCalendar(1998, Calendar.JANUARY, 1).getTime(), 48985);
        addCar(db, 54, "GL493194", new GregorianCalendar(2016, Calendar.JANUARY, 1).getTime(), 157522);
        addCar(db, 58, "BS266231", new GregorianCalendar(2004, Calendar.JANUARY, 1).getTime(), 109853);
        addCar(db, 1, "SH64419", new GregorianCalendar(1998, Calendar.JANUARY, 1).getTime(), 134883);
        addCar(db, 48, "TG248103", new GregorianCalendar(2000, Calendar.JANUARY, 1).getTime(), 206004);
        addCar(db, 47, "BL364524", new GregorianCalendar(2009, Calendar.JANUARY, 1).getTime(), 77781);
        addCar(db, 59, "BL86440", new GregorianCalendar(1996, Calendar.JANUARY, 1).getTime(), 82925);
        addCar(db, 52, "AI464143", new GregorianCalendar(2003, Calendar.JANUARY, 1).getTime(), 204690);
        addCar(db, 39, "ZG465560", new GregorianCalendar(2011, Calendar.JANUARY, 1).getTime(), 210021);
        addCar(db, 50, "SO494166", new GregorianCalendar(1987, Calendar.JANUARY, 1).getTime(), 45509);
        addCar(db, 2, "VS440631", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), 30994);
        addCar(db, 43, "OW212063", new GregorianCalendar(2016, Calendar.JANUARY, 1).getTime(), 198498);
        addCar(db, 46, "SG497853", new GregorianCalendar(2000, Calendar.JANUARY, 1).getTime(), 153464);
        addCar(db, 10, "OW165403", new GregorianCalendar(2011, Calendar.JANUARY, 1).getTime(), 88156);
        addCar(db, 30, "JU87377", new GregorianCalendar(1989, Calendar.JANUARY, 1).getTime(), 91670);
        addCar(db, 56, "ZG315075", new GregorianCalendar(2006, Calendar.JANUARY, 1).getTime(), 39841);
        addCar(db, 25, "SH322105", new GregorianCalendar(1991, Calendar.JANUARY, 1).getTime(), 114875);
        addCar(db, 6, "OW413244", new GregorianCalendar(2013, Calendar.JANUARY, 1).getTime(), 121495);
        addCar(db, 25, "SG52744", new GregorianCalendar(1989, Calendar.JANUARY, 1).getTime(), 184394);
        addCar(db, 59, "VD362538", new GregorianCalendar(1990, Calendar.JANUARY, 1).getTime(), 169072);
        addCar(db, 41, "UR390329", new GregorianCalendar(2001, Calendar.JANUARY, 1).getTime(), 27887);
        addCar(db, 32, "GL160590", new GregorianCalendar(2015, Calendar.JANUARY, 1).getTime(), 121179);
        addCar(db, 18, "AG406073", new GregorianCalendar(1986, Calendar.JANUARY, 1).getTime(), 142801);
        addCar(db, 42, "SZ296478", new GregorianCalendar(2013, Calendar.JANUARY, 1).getTime(), 98587);
        addCar(db, 16, "BS142264", new GregorianCalendar(2014, Calendar.JANUARY, 1).getTime(), 96170);
        addCar(db, 47, "NE63146", new GregorianCalendar(2011, Calendar.JANUARY, 1).getTime(), 153691);
        addCar(db, 50, "NW427084", new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), 190928);
        addCar(db, 38, "TI331739", new GregorianCalendar(2014, Calendar.JANUARY, 1).getTime(), 120531);
    }

    /**
     * Adds all base technicians to the database
     * @param db Database
     */
    private static void addTechnicians(AppDatabase db) {
        db.technicianDao().deleteAll();

        addTechnician(db, "M.", "Fred", "Jones", "fred.jones@sbg.com");
        addTechnician(db, "M.", "Shaggy", "Rogers", "shaggy.rogers@sbg.com");
        addTechnician(db, "Ms.", "Vera", "Dinkley", "vera.dinkley@sbg.com");
        addTechnician(db, "Ms.", "Daphne", "Blake", "daphne.blake@sbg.com");
        addTechnician(db, "M.", "Scooby", "Doo", "scooby.doo@sbg.com");
    }

    /**
     * Adds all base revisions to the database
     * @param db Database
     */
    private static void addRevisions(AppDatabase db) {
        db.revisionDao().deleteAll();

        addRevision(db, 3, 3, new GregorianCalendar(2022, Calendar.JUNE, 9, 10, 30).getTime(), new GregorianCalendar(2022, Calendar.JUNE, 9, 17, 0).getTime(), "Finished");
        addRevision(db, 1, 10, new GregorianCalendar(2022, Calendar.JULY, 3, 14, 30).getTime(), new GregorianCalendar(2022, Calendar.JULY, 4, 10, 45).getTime(), "Finished");
        addRevision(db, 1, 23, new GregorianCalendar(2022, Calendar.AUGUST, 14, 13, 30).getTime(), new GregorianCalendar(2022, Calendar.AUGUST, 13, 18, 10).getTime(), "Finished");
        addRevision(db, 3, 18, new GregorianCalendar(2022, Calendar.OCTOBER, 8, 8, 45).getTime(), new GregorianCalendar(2022, Calendar.OCTOBER, 8, 12, 0).getTime(), "Finished");
        addRevision(db, 4, 7, new GregorianCalendar(2022, Calendar.OCTOBER, 14, 11, 30).getTime(), null, "Processing");
        addRevision(db, 2, 4, new GregorianCalendar(2022, Calendar.OCTOBER, 17, 14, 0).getTime(), null, "Processing");
        addRevision(db, 3, 5, new GregorianCalendar(2022, Calendar.OCTOBER, 10, 10, 10).getTime(), null, "Processing");
        addRevision(db, 1, 9, new GregorianCalendar(2022, Calendar.NOVEMBER, 23, 14, 30).getTime(), null, "Awaiting");
        addRevision(db, 5, 1, new GregorianCalendar(2022, Calendar.DECEMBER, 11, 8, 45).getTime(), null, "Awaiting");
    }

    /**
     * Populates the database with the base data
     * @param db Database
     */
    private static void populateWithBaseData(AppDatabase db) {
        addBrands(db);
        addModels(db);
        addCantons(db);
        addCars(db);
        addTechnicians(db);
        addRevisions(db);
    }

    /**
     * Async class for base data insertion
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final AppDatabase db;

        /**
         * Async class for base data insertion constructor
         * @param db Database
         */
        PopulateDbAsync(AppDatabase db) {
            this.db = db;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            populateWithBaseData(db);
            return null;
        }
    }
}