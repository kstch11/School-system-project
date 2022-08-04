# Sis app

#	Představení projektu

Systém je zjednodušenou analogií KOS, která podporuje přidávání kurzů, jejich prohlížení a úpravy. Učitelé mohou vytvořit kurz a vybrat pro něj učebny, což omezuje kapacitu kurzu. Studenti se nemohou zapsat do kurzu, pokud v kurzu nezbývají žádná další volná místa, studenti si mohou také prohlédnout kurzy, kterých se účastní. Učitelé si zase mohou zobrazit kurzy, které vyučují. Na konci kurzu mohou učitelé dát známky za kurz, které studenti absolvovali. Na základě udělených známek se vypočítá průměrnou známku, podle kterého se rozhodne, zda student postoupí do dalšího kurzu, nebo ne. Portál bude také podporovat systém rozesílání zprav souvisejících se studiem (změny v rozvrhu, zrušení vyučování atd.).

# Uživatelé a jejich funkce

V systému jsou 4 typy uživatelů. V zásadě budou použity 2 role: Student a Teacher.
Admin bude mít možnost upravovat všechna data a přidělovat role.
Role User existuje jenom před přidělováním role adminem.

* Student

Studenti se mohou přihlásit do kurzu, vytvořit si vlastní rozvrh a rezervovat předměty pro příští semestr. Studenti budou informováni o novinkách.

* Teacher

Učitelé mohou vytvořit kurz, který se automaticky zapisuje do jejich rozvrhu, také jejich funkce zahrnuje vytváření zpráv a hodnocení studentů.

* Admin

Admin přidělává registrovaným uživatelům role (student, učitel) a potvrzuje vytvoření zprávy.

# Softwarové informace

V rámci semestrálního projektu bude tento systém implementovaný jako backend aplikace v jazyku Java, s využitím SpringBootu. Aplikace bude napojená na relační databázi PostgreSQL a bude s ní možné komunikovat prostřednictvím HTTP dotazů.
