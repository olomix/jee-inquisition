package edu.anxolerd.inquisition.core;


public class DbHelper {
    public static final String INIT_SQL = (
        "CREATE EXTENSION IF NOT EXISTS pgcrypto;\n" +
        "CREATE TABLE IF NOT EXISTS interest (\n" +
        "  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),\n" +
        "  title TEXT NOT NULL,\n" +
        "  description TEXT,\n" +
        "  sin_rate INTEGER NOT NULL\n" +
        ");\n" +
        "CREATE TABLE IF NOT EXISTS person (\n" +
        "  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),\n" +
        "  first_name TEXT NOT NULL,\n" +
        "  middle_name TEXT,\n" +
        "  last_name TEXT NOT NULL,\n" +
        "  birth_date DATE NOT NULL,\n" +
        "  death_date DATE\n" +
        ");\n" +
        "CREATE TABLE IF NOT EXISTS m2m_person_interest (" +
        "  person_id UUID NOT NULL,\n" +
        "  interest_id UUID NOT NULL\n" +
        ");\n" +
        "ALTER TABLE m2m_person_interest ADD CONSTRAINT m2m_person_interest_person_id_fk\n" +
        "  FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE;\n" +
        "ALTER TABLE m2m_person_interest ADD CONSTRAINT m2m_person_interest_interest_id_fk\n" +
        "  FOREIGN KEY (interest_id) REFERENCES interest(id) ON DELETE CASCADE;\n" +
        "ALTER TABLE m2m_person_interest ADD CONSTRAINT m2m_person_interest_person_id_interest_id_ix\n" +
        "  UNIQUE (person_id, interest_id);\n" +
        "CREATE OR REPLACE FUNCTION calc_sin_rate(person_id UUID) RETURNS INTEGER AS $$\n" +
        "  SELECT " +
        "    sum(interest.sin_rate) :: INTEGER\n" +
        "  FROM m2m_person_interest\n" +
        "    JOIN interest ON m2m_person_interest.interest_id = interest.id\n" +
        "  WHERE m2m_person_interest.person_id = $1;\n" +
        "$$ LANGUAGE 'sql';\n" +
        "CREATE TABLE IF NOT EXISTS \"case\" (\n" +
        "  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),\n" +
        "  suspect_person_id UUID NOT NULL,\n" +
        "  date_opened TIMESTAMP WITHOUT TIME ZONE NOT NULL,\n" +
        "  status INTEGER NOT NULL DEFAULT 1,\n" +
        "  accuses TEXT NOT NULL,\n" +
        "  date_closed TIMESTAMP WITHOUT TIME ZONE,\n" +
        "  verdict INTEGER,\n" +
        "  sentence TEXT\n" +
        ");\n" +
        "ALTER TABLE \"case\" ADD CONSTRAINT case_suspect_person_id_fk " +
        "  FOREIGN KEY (suspect_person_id) REFERENCES person(id);\n"
    );

    public static final String DROP_SQL = (
        "DROP TABLE \"case\" CASCADE;\n" +
        "DROP FUNCTION calc_sin_rate(person_id UUID);\n" +
        "DROP TABLE m2m_person_interest CASCADE;\n" +
        "DROP TABLE person CASCADE;\n" +
        "DROP TABLE interest CASCADE;\n" +
        "DROP EXTENSION pgcrypto;\n"
    );
}
