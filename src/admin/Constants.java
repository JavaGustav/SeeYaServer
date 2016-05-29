package admin;

/**
 * SeeYa admin
 * @author Gustav Frigren
 *
 */
public class Constants {

	//examples
		// TYPE, nyckel som ska finnas med i varje JSON-objekt/str�ng f�r identifiering av vilken slags meddelande
		// eller kommando objektet/filen inneh�ller.
		public static final String TYPE = "1";
		
		// TYPE f�r appens beg�ran om att registrera en ny anv�ndare.
		public static final String NEWUSER = "2";
		
		// TYPE f�r appens beg�ran om att logga in en anv�ndare.
		public static final String LOGIN ="3";
		
		// TYPE f�r appens beg�ran om att f� en activity. Detta sker n�r anv�ndaren trycker p� en headline i appen
		// f�r att se sj�lva aktiviteten.
		public static final String ACTIVITIY = "4";
		
		public static final String ACTIVITY_HEADLINES = "5"; // TODO remove?
		
		// Key vid appens beg�ran om att logga in eller skapa ny anv�ndare.
		public static final String USERNAME = "6";
		
		// Key vid appens beg�ran om att logga in eller skapa ny anv�ndare.
		public static final String PASSWORD = "7";
		
		// Key vid appens beg�ran om att skapa ny anv�ndare.
		public static final String EMAIL = "8";
		
		public static final String CONFIRMATION = "9";			//TODO remove?
		
		// Key vid s�ndande av annat �n standardmeddelande.
		public static final String MESSAGE = "10";
		public static final String CONFIRMATION_TYPE = "11";	//TODO remove?

		// TYPE vid felmeddelande fr�n servern. Fins flera felmeddelanden. Ska m�jligen tas bort.
		public static final String ERROR = "12";
		public static final String ERROR_TYPE = "13";			//TODO remove?

		// TYPE vid appens beg�ran om att skapa ny aktivitet.
		public static final String NEWACTIVITY = "14";
		
		// Key vid tex appens beg�ran om att skapa ny aktivitet.
		public static final String NAME = "15";
		
		// Key vid appens beg�ran om att skapa ny aktivitet. Anv�nds f�r platsen d�r aktiviteten ska utf�ras.
		public static final String PLACE = "16";
		
		// Key vid appens beg�ran om att skapa ny aktivitet. Anv�nds f�r starttiden f�r aktiviteten.
		public static final String TIME = "17";
		
		// Key vid appens beg�ran om att skapa ny aktivitet. Anv�nds f�r att ange max antal deltagare i aktiviteten.
		public static final String MAX_NBROF_PARTICIPANTS = "18";
		
		// Key vid angivandet av subkategori
		public static final String SUBCATEGORY = "19";
		
		// Key vid angivandet av minsta antalet deltagare. Anv�nds av appen vid skapandet av ny aktivitet.
		public static final String MIN_NBR_OF_PARTICIPANTS = "20";
		
		// TYPE f�r appens beg�ran om att publisera en aktivitet till alla anv�ndare av systemet, publik
		public static final String PUBLISH_ACTIVITY = "21";
		
		// Key f�r angivandet av n�got id, tex aktivitets-id.
		public static final String ID = "22";
		
		// Key f�r angivandet av datum.
		public static final String DATE = "23";
		
		// Key f�r angivandet av headline/rubrik p� aktivitet.
		public static final String HEADLINE = "24";
		
		// Key f�r angivandet av den anv�ndare som skapat/�ger aktiviteten.
		public static final String ACTIVITY_OWNER = "25";
		
		
		public static final String ACTIVITY_CATEGORIES = "26";
		public static final String LOCATIONS = "27";
		public static final String MAINCATEGORY = "28";
		public static final String LOCATIONS_VERSION_NBR = "29";
		
		//
		public static final String ARRAY_SUBCATEGORY = "30";
		
		// Key f�r serverns retur av huvudkategorier i form av en array. Ing�r i svar p� appens beg�ran om 
		// GET_MAINCATEGORY_SUBCATEGORY_HEADLINES_FOR_USER och 
		// GET_MAINCATEGORY_SUBCATEGORY_HEADLINES_FOR_USER_OWND_ACTIVITIES
		public static final String ARRAY_MAINCATEGORY = "31";
		
		// Key f�r serverns retur av headlines i form av en array. Ing�r i svar p� appens beg�ran om 
		// GET_MAINCATEGORY_SUBCATEGORY_HEADLINES_FOR_USER och 
		// GET_MAINCATEGORY_SUBCATEGORY_HEADLINES_FOR_USER_OWND_ACTIVITIES
		public static final String ARRAY_HEADLINE = "32";
		public static final String NBR_OF_SIGNEDUP = "33";
		public static final String MY_ACTIVITIES = "34";
		public static final String ARRAY_LANDSCAPE = "35";
		public static final String ARRAY_CITY = "36";
		public static final String LOGIN_OK = "37";
		public static final String LOGIN_FAIL = "38";
		public static final String DATE_PUBLISHED = "39";
		public static final String LOCATIONS_CONFIRMATION = "40";
		public static final String CATEGORIES_CONFIRMATION = "41";
		public static final String CATEGORIES_VERSION_NUMBER = "42";
		
		// TYPE f�r appens beg�ran am att en anv�ndare ska delta i en aktivitet.
		public static final String SIGNUP = "43";
		
		// TYPE f�r appens beg�ran om att en anv�ndare ska avregisterera sig fr�n en aktivitet.
		public static final String UNREGISTER_FROM_ACTIVITY = "44";
		
		// TYPE f�r serverns svar p� att en anv�ndare anm�lts till en aktivitet.
		public static final String SIGNUP_CONFIRMATION = "45";
		
		// TYPE f�r serverns svar p� att en anv�ndare inte kunde anm�las till en aktivitet.
		public static final String SIGNUP_ERROR = "46";
		
		// TYPE f�r serverns svar p� att en aktivitet har publicerats. Svar p� appens beg�ran om PUBLISH_ACTIVITY
		// eller PUBLISH_ACTIVITY_TO_SPECIFIC_USERS
		public static final String PUBLISH_ACTIVITY_CONFIRMATION = "47";
		
		// TYPE f�r servens svar p� att en aktivitet inte lyckats publiceras. Svar p� appens beg�ran om PUBLISH_ACTIVITY
		// eller PUBLISH_ACTIVITY_TO_SPECIFIC_USERS
		public static final String PUBLISH_ACTIVITY_ERROR = "48";
		
		// TYPE f�r servens svar p� att en ny aktivitet har skapats. Svar p� appens beg�ran om NEWACTIVITY
		public static final String NEW_ACTIVTIY_CONFIRMATION = "49";
		
		// TYPE f�r serverns svar p� att en ny aktivitet inte lyckats skapas. Svar p� appens beg�ran om NEWACTIVITY
		public static final String NEW_ACTIVITY_ERROR = "50";
		
		// TYPE f�r serverns svar p� att en ny anv�ndare har skapats. Svar p� appens beg�ran om NEWUSER
		public static final String NEW_USER_CONFIRMATION = "51";
		
		// TYPE f�r serverns svar p� att en ny anv�ndare inte lyckades skapas. Svar p� appens beg�ran om NEWUSER
		public static final String NEW_USER_ERROR = "52";
		
		//TYPE f�r appens beg�ran om att f� huvudkategori, subkategori och aktivitetsrubriker f�r en viss anv�ndare.
		// d�r anv�ndaren �r inbjuden till aktiviteter.
		public static final String GET_MAINCATEGORY_SUBCATEGORY_HEADLINES_FOR_USER = "53";
		
		//TYPE f�r appens beg�ran om att f� huvudkategori, subkategori och aktivitetsrubriker f�r en viss anv�ndare.
		// d�r anv�ndaren har skapat/�ger aktiviteterna.
		public static final String GET_MAINCATEGORY_SUBCATEGORY_HEADLINES_FOR_USER_OWND_ACTIVITIES = "54";
		
		//TYPE f�r svar fr�n servern p� beg�ran av typ: GET_MAINCATEGORY_SUBCATEGORY_HEADLINES_FOR_USER.
		public static final String MAINCATEGORY_SUBCATEGORY_HEADLINES_FOR_USER = "55";
		
		//TYPE f�r svar fr�n servern p� beg�ran av typ: GET_MAINCATEGORY_SUBCATEGORY_HEADLINES_FOR_USER_OWND_ACTIVITIES.
		public static final String MAINCATEGORY_SUBCATEGORY_HEADLINES_FOR_USER_OWND_ACTIVITIES = "56";
		
		// TYPE f�r appens beg�ran om att publisera aktivitet f�r andra �n alla anv�ndare, dvs till utpekade anv�ndare
		public static final String PUBLISH_ACTIVITY_TO_SPECIFIC_USERS = "57";
		
		// TYPE f�r appens beg�ran om att kontrollera om en anv�ndare finns i databasen.
		public static final String CHECK_IF_USER_EXISTS = "58";
		
		// TYPE f�r serverns svar p� fr�gan CHECK_IF_USER_EXISTS, om anv�ndaren finns i databasen.
		public static final String USER_EXISTS = "59";
		
		// TYPE f�r serverns svar p� fr�gan CHECK_IF_USER_EXISTS, om anv�ndaren inte finns i databasen.
		public static final String INVALID_USERNAME = "60";
		
		// TYPE f�r serverns svar p� appens beg�ran om att avregistera en anv�ndare fr�n en aktivitet, om detta g�tt bra.
		public static final String UNREGISTER_FROM_ACTIVITY_CONFIRMATION = "61";
		
		// TYPE f�r serverns svar p� appens beg�ran om att avregistrera en anv�ndare fr�n en aktivite, om detta inte g�tt bra.
		public static final String UNREGISTER_FROM_ACTIVITY_ERROR = "62";
		
		// Anv�nds tillsammans med commando/TYPE PUBLISH_ACTIVITY_TO_SPECIFIC_USERS. Inneh�ller en array med 
		// de anv�ndare som aktiviteten ska publiseras till.
		public static final String ARRAY_USERNAME = "63";
		
		// Anv�nds av servern som nyckel f�r om en anv�ndare �r signed up f�r en aktivitet eller inte.
		public static final String SIGNED_UP = "64";
		
		// Anv�nds som value till SIGNED_UP om anv�ndaren �r signed up f�r aktiviteten.
		public static final String YES = "65";
		
		// Anv�nds som value till SIGNED_UP om anv�ndaren inte �r signed up f�r aktiviteten.
		public static final String NO = "66";
		
		// Anv�nds som address vid inbjudan till aktivitet och n�r man h�mtar en aktivitet
		public static final String ADDRESS = "67";
		
		// used by SeeYa Admin
		public static final String GET_ALL_USERS = "68";
		public static final String NEW_MAIN_CATEGORY = "69";
		public static final String NEW_SUB_CATEGORY = "70";
		public static final String GET_USER_EMAIL = "71";

		public static final String LOG_INFO = "INFO";
		public static final String LOG_ERROR = "ERROR";
	
}
