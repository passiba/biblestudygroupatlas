CREATE TABLE adress (
  adress_id bigint(20) NOT NULL AUTO_INCREMENT,
  addr1 varchar(80) NOT NULL,
  addr2 varchar(45) DEFAULT NULL,
  city varchar(80) NOT NULL,
  country varchar(20) NOT NULL,
  location_lat double DEFAULT NULL,
  location_lng double DEFAULT NULL,
  phone varchar(80) NOT NULL,
  state varchar(80) NOT NULL,
  zip varchar(20) NOT NULL,
  PRIMARY KEY (adress_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


  create table biblesession (
        biblesessionid bigint not null AUTO_INCREMENT,
        sessiontime datetime not null,
        primary key (biblesessionid)
     ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


    create table bibletranslation (
        bible_translation_id bigint not null AUTO_INCREMENT,
        created_by varchar(255),
        created_on datetime,
        updated_by varchar(255),
        updated_on datetime,
        bible_abbrv varchar(70) not null,
        bible_name varchar(200) not null,
        published_date date,
        publisher_name varchar(100) not null,
        primary key (bible_translation_id)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;



    create table book (
        book_id bigint not null AUTO_INCREMENT,
        book_num integer,
        book_text varchar(200) not null,
        fk_section_id bigint not null,
        primary key (book_id)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;



    create table bookdatasource (
        bookdatasource_id bigint not null AUTO_INCREMENT,
        created_by varchar(255),
        created_on datetime,
        updated_by varchar(255),
        updated_on datetime,
        bookname varchar(50) not null,
        catalog_dir varchar(50) not null,
        hostname varchar(50) not null,
        package_dir varchar(50) not null,
        proxy_host varchar(255),
        proxy_port varchar(255),
        sitename varchar(50) not null,
        status varchar(50) not null,
        fk_bible_translation_id bigint,
        primary key (bookdatasource_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;



    create table booksection (
        section_id bigint not null AUTO_INCREMENT,
        section varchar(40) not null,
        fk_bible_translation_id bigint,
        primary key (section_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


    create table chapter (
        chapter_id bigint not null AUTO_INCREMENT,
        chapter_num integer,
        chapter_title text not null,
        fk_book_id bigint not null,
        primary key (chapter_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;



    create table groupperson (
        fk_person_id bigint not null,
        fk_group_id bigint not null,
        primary key (fk_group_id, fk_person_id)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;



    create table groups (
        group_id bigint not null AUTO_INCREMENT,
        congregatiolistemailaddress varchar(70),
        congregationname varchar(60) not null,
        congregationwebsiteurl varchar(70),
        description varchar(50),
        grouptypename varchar(50) not null,
        name varchar(50) not null,
        status varchar(50) not null,
        fk_group_adress_id bigint,
        primary key (group_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;



    create table groupservice (
        groupservice_id bigint not null AUTO_INCREMENT,
        created_by varchar(255),
        created_on datetime,
        updated_by varchar(255),
        updated_on datetime,
        servicename varchar(80) not null,
        fk_status_id bigint,
        primary key (groupservice_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;



    create table groupsession (
        fk_group_id bigint not null,
        fk_biblesessionid bigint not null,
        primary key (fk_group_id, fk_biblesessionid)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;



    create table person (
        person_id bigint not null AUTO_INCREMENT,
        dateofbirth date not null,
        email varchar(80) not null,
        firstname varchar(80) not null,
        lastname varchar(80) not null,
        fk_address_id bigint,
        fk_userid bigint,
        primary key (person_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;



    create table personsession (
        fk_person_id bigint not null,
        fk_biblesessionid bigint not null,
        primary key (fk_person_id, fk_biblesessionid)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;



    create table status (
        status_id bigint not null AUTO_INCREMENT,
        statusname varchar(20) not null,
        primary key (status_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


    create table users (
        userid bigint not null AUTO_INCREMENT,
        password varchar(255),
        rolename varchar(255),
        status varchar(255),
        username varchar(255),
        primary key (userid)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    create table verse (
        verse_id bigint not null AUTO_INCREMENT,
        verse_num integer not null,
        verse_text text not null,
        fk_chapter_id bigint not null,
        primary key (verse_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    alter table book 
        add index FK2E3AE96F79E6B5 (fk_section_id), 
        add constraint FK2E3AE96F79E6B5 
        foreign key (fk_section_id) 
        references booksection (section_id);

    alter table bookdatasource 
        add index FKF5C7DBEE31C41AE1 (fk_bible_translation_id), 
        add constraint FKF5C7DBEE31C41AE1 
        foreign key (fk_bible_translation_id) 
        references bibletranslation (bible_translation_id);

    alter table booksection 
        add index FK159DDBDC31C41AE1 (fk_bible_translation_id), 
        add constraint FK159DDBDC31C41AE1 
        foreign key (fk_bible_translation_id) 
        references bibletranslation (bible_translation_id);

    alter table chapter 
        add index FK2C0C7C4D81600C76 (fk_book_id), 
        add constraint FK2C0C7C4D81600C76 
        foreign key (fk_book_id) 
        references book (book_id);

    alter table groupperson 
        add index FK5DEB76941C688519 (fk_person_id), 
        add constraint FK5DEB76941C688519 
        foreign key (fk_person_id) 
        references person (person_id);

    alter table groupperson 
        add index FK5DEB76943CC556B1 (fk_group_id), 
        add constraint FK5DEB76943CC556B1 
        foreign key (fk_group_id) 
        references groups (group_id);

    alter table groups 
        add index FKB63DD9D414316739 (fk_group_adress_id), 
        add constraint FKB63DD9D414316739 
        foreign key (fk_group_adress_id) 
        references adress (adress_id);

    alter table groupservice 
        add index FKFE375356741363F9 (fk_status_id), 
        add constraint FKFE375356741363F9 
        foreign key (fk_status_id) 
        references status (status_id);

    alter table groupsession 
        add index FKFE440F3712A8A6EF (fk_biblesessionid), 
        add constraint FKFE440F3712A8A6EF 
        foreign key (fk_biblesessionid) 
        references biblesession (biblesessionid);

    alter table groupsession 
        add index FKFE440F373CC556B1 (fk_group_id), 
        add constraint FKFE440F373CC556B1 
        foreign key (fk_group_id) 
        references groups (group_id);

    alter table person 
        add index FKC4E39B55AC8945EF (fk_address_id), 
        add constraint FKC4E39B55AC8945EF 
        foreign key (fk_address_id) 
        references adress (adress_id);

    alter table person 
        add index FKC4E39B55408B4B4F (fk_userid), 
        add constraint FKC4E39B55408B4B4F 
        foreign key (fk_userid) 
        references users (userid);

    alter table personsession 
        add index FKCFFC6DA11C688519 (fk_person_id), 
        add constraint FKCFFC6DA11C688519 
        foreign key (fk_person_id) 
        references person (person_id);

    alter table personsession 
        add index FKCFFC6DA112A8A6EF (fk_biblesessionid), 
        add constraint FKCFFC6DA112A8A6EF 
        foreign key (fk_biblesessionid) 
        references biblesession (biblesessionid);

    alter table verse 
        add index FK6AE793514BB75BE (fk_chapter_id), 
        add constraint FK6AE793514BB75BE 
        foreign key (fk_chapter_id) 
        references chapter (chapter_id);
