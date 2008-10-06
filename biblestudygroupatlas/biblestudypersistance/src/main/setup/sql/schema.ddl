
    create table adress (
        adress_id bigint not null auto_increment,
        created_by varchar(255),
        created_on datetime,
        updated_by varchar(255),
        updated_on datetime,
        addr1 varchar(80) not null,
        addr2 varchar(40),
        city varchar(80) not null,
        country varchar(20) not null,
        location_lat double precision,
        location_lng double precision,
        phone varchar(80) not null,
        state varchar(80) not null,
        zip varchar(20) not null,
        primary key (adress_id)
    ) type=InnoDB;

    create table biblesession (
        biblesessionid bigint not null auto_increment,
        created_by varchar(255),
        created_on datetime,
        updated_by varchar(255),
        updated_on datetime,
        sessiontime datetime not null,
        primary key (biblesessionid)
    ) type=InnoDB;

    create table bibletranslation (
        bible_translation_id bigint not null auto_increment,
        created_by varchar(255),
        created_on datetime,
        updated_by varchar(255),
        updated_on datetime,
        bible_abbrv varchar(70) not null,
        bible_name varchar(200) not null,
        published_date date not null,
        publisher_name varchar(100) not null,
        primary key (bible_translation_id)
    ) type=InnoDB;

    create table book (
        book_id bigint not null auto_increment,
        created_by varchar(255),
        created_on datetime,
        updated_by varchar(255),
        updated_on datetime,
        book_num integer not null,
        book_text varchar(200) not null,
        fk_section_id bigint not null,
        fk_bookdatasource_id bigint,
        primary key (book_id)
    ) type=InnoDB;

    create table bookdatasource (
        bookdatasource_id bigint not null auto_increment,
        created_by varchar(255),
        created_on datetime,
        updated_by varchar(255),
        updated_on datetime,
        weburlname varchar(50) not null,
        status varchar(10) not null,
        primary key (bookdatasource_id)
    ) type=InnoDB;

    create table booksection (
        section_id bigint not null auto_increment,
        created_by varchar(255),
        created_on datetime,
        updated_by varchar(255),
        updated_on datetime,
        section varchar(40) not null,
        fk_bible_translation_id bigint,
        primary key (section_id)
    ) type=InnoDB;

    create table chapter (
        chapter_id bigint not null auto_increment,
        created_by varchar(255),
        created_on datetime,
        updated_by varchar(255),
        updated_on datetime,
        chapter_num integer not null,
        chapter_title varchar(200) not null,
        fk_book_id bigint not null,
        primary key (chapter_id)
    ) type=InnoDB;

    create table groupperson (
        fk_person_id bigint not null,
        fk_group_id bigint not null,
        primary key (fk_group_id, fk_person_id)
    ) type=InnoDB;

    create table groups (
        group_id bigint not null auto_increment,
        created_by varchar(255),
        created_on datetime,
        updated_by varchar(255),
        updated_on datetime,
        congregatiolistemailaddress varchar(70),
        congregationname varchar(60) not null,
        congregationwebsiteurl varchar(70),
        description varchar(50),
        grouptypename varchar(50) not null,
        name varchar(50) not null,
        status varchar(50) not null,
        fk_group_adress_id bigint,
        primary key (group_id)
    ) type=InnoDB;

    create table groupservice (
        groupservice_id bigint not null auto_increment,
        created_by varchar(255),
        created_on datetime,
        updated_by varchar(255),
        updated_on datetime,
        servicename varchar(80) not null,
        fk_status_id bigint,
        primary key (groupservice_id)
    ) type=InnoDB;

    create table groupsession (
        fk_group_id bigint not null,
        fk_biblesessionid bigint not null,
        primary key (fk_group_id, fk_biblesessionid)
    ) type=InnoDB;

    create table person (
        person_id bigint not null auto_increment,
        created_by varchar(255),
        created_on datetime,
        updated_by varchar(255),
        updated_on datetime,
        dateofbirth date not null,
        email varchar(80) not null,
        firstname varchar(80) not null,
        lastname varchar(80) not null,
        fk_userid bigint,
        fk_address_id bigint,
        primary key (person_id)
    ) type=InnoDB;

    create table personsession (
        fk_person_id bigint not null,
        fk_biblesessionid bigint not null,
        primary key (fk_person_id, fk_biblesessionid)
    ) type=InnoDB;

    create table status (
        status_id bigint not null auto_increment,
        statusname varchar(20) not null,
        primary key (status_id)
    ) type=InnoDB;

    create table users (
        userid bigint not null auto_increment,
        created_by varchar(255),
        created_on datetime,
        updated_by varchar(255),
        updated_on datetime,
        password varchar(255),
        rolename varchar(255),
        status varchar(255),
        username varchar(255),
        primary key (userid)
    ) type=InnoDB;

    create table verse (
        verse_id bigint not null auto_increment,
        created_by varchar(255),
        created_on datetime,
        updated_by varchar(255),
        updated_on datetime,
        verse_num integer not null,
        verse_text text not null,
        fk_biblesessionid bigint,
        fk_chapter_id bigint not null,
        primary key (verse_id)
    ) type=InnoDB;

    alter table book 
        add index FK2E3AE9E3A20654 (fk_bookdatasource_id), 
        add constraint FK2E3AE9E3A20654 
        foreign key (fk_bookdatasource_id) 
        references bookdatasource (bookdatasource_id);

    alter table book 
        add index FK2E3AE96F79E6B5 (fk_section_id), 
        add constraint FK2E3AE96F79E6B5 
        foreign key (fk_section_id) 
        references booksection (section_id);

    alter table bookdatasource 
        add index FKF5C7DBEE741363F9 (fk_status_id), 
        add constraint FKF5C7DBEE741363F9 
        foreign key (fk_status_id) 
        references status (status_id);

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
        add index FK6AE793512A8A6EF (fk_biblesessionid), 
        add constraint FK6AE793512A8A6EF 
        foreign key (fk_biblesessionid) 
        references biblesession (biblesessionid);

    alter table verse 
        add index FK6AE793514BB75BE (fk_chapter_id), 
        add constraint FK6AE793514BB75BE 
        foreign key (fk_chapter_id) 
        references chapter (chapter_id);


 insert into  bibletranslation(
             created_by,updated_by,created_on,updated_on, bible_abbrv,bible_name, published_date,publisher_name)
        values('Admin','Admin',curdate(),curdate(),'ESV','English Standard Version',20010101,'Ministry of Good News Publishers: Crossway bibles');



insert into  booksection(
             created_by,updated_by,created_on,updated_on, section,fk_bible_translation_id)
        values('Admin','Admin',curdate(),curdate(),'New Testament',1);

insert into  booksection(
             created_by,updated_by,created_on,updated_on, section,fk_bible_translation_id)
        values('Admin','Admin',curdate(),curdate(),'Old Testament',1);


insert into  bibletranslation(
             created_by,updated_by,created_on,updated_on, bible_abbrv,bible_name, published_date,publisher_name)
        values('Admin','Admin',curdate(),curdate(),'EVL 1992
','vuoden 1992 suomenkielinen raamattu',19920101,'Kirkon keskusrahasto');


insert into  booksection(
             created_by,updated_by,created_on,updated_on, section,fk_bible_translation_id)
        values('Admin','Admin',curdate(),curdate(),'Uusi Testamentti',2);

insert into  booksection(
             created_by,updated_by,created_on,updated_on, section,fk_bible_translation_id)
        values('Admin','Admin',curdate(),curdate(),'Vanha Testamentti',2);



insert into  bookdatasource(
             created_by,updated_by,created_on,updated_on,  weburlname,
        status)
        values('Admin','Admin',curdate(),curdate(),'http://ftp.funet.fi/index/bible/fi/1992/Matt.html','Aktiivinen');

insert into  bookdatasource(
             created_by,updated_by,created_on,updated_on,  weburlname,
        status)
        values('Admin','Admin',curdate(),curdate(),'http://ftp.funet.fi/index/bible/fi/1992/Mark.html','Aktiivinen');

insert into  bookdatasource(
             created_by,updated_by,created_on,updated_on,  weburlname,
        status)
        values('Admin','Admin',curdate(),curdate(),'http://ftp.funet.fi/index/bible/fi/1992/Luuk.html','Aktiivinen');


insert into  bookdatasource(
             created_by,updated_by,created_on,updated_on,  weburlname,
        status)
        values('Admin','Admin',curdate(),curdate(),'http://ftp.funet.fi/index/bible/fi/1992/Joh.html','Aktiivinen');


insert into  bookdatasource(
             created_by,updated_by,created_on,updated_on,  weburlname,
        status)
        values('Admin','Admin',curdate(),curdate(),'http://ftp.funet.fi/index/bible/fi/1992/Apt.html','Aktiivinen');

insert into  bookdatasource(
             created_by,updated_by,created_on,updated_on,  weburlname,
        status)
        values('Admin','Admin',curdate(),curdate(),'http://ftp.funet.fi/index/bible/fi/1992/Room.html','Aktiivinen');

insert into  bookdatasource(
             created_by,updated_by,created_on,updated_on,  weburlname,
        status)
        values('Admin','Admin',curdate(),curdate(),'http://ftp.funet.fi/index/bible/fi/1992/1Kor.html','Aktiivinen');

insert into  bookdatasource(
             created_by,updated_by,created_on,updated_on,  weburlname,
        status)
        values('Admin','Admin',curdate(),curdate(),'http://ftp.funet.fi/index/bible/fi/1992/2Kor.html','Aktiivinen');

insert into  bookdatasource(
             created_by,updated_by,created_on,updated_on,  weburlname,
        status)
        values('Admin','Admin',curdate(),curdate(),'http://ftp.funet.fi/index/bible/fi/1992/Gal.html','Aktiivinen');

insert into  bookdatasource(
             created_by,updated_by,created_on,updated_on,  weburlname,
        status)
        values('Admin','Admin',curdate(),curdate(),'http://ftp.funet.fi/index/bible/fi/1992/Ef.html','Aktiivinen');

insert into  bookdatasource(
             created_by,updated_by,created_on,updated_on,  weburlname,
        status)
        values('Admin','Admin',curdate(),curdate(),'http://ftp.funet.fi/index/bible/fi/1992/Fil.html','Aktiivinen');

insert into  bookdatasource(
             created_by,updated_by,created_on,updated_on,  weburlname,
        status)
        values('Admin','Admin',curdate(),curdate(),'http://ftp.funet.fi/index/bible/fi/1992/Kol.html','Aktiivinen');

insert into  bookdatasource(
             created_by,updated_by,created_on,updated_on,  weburlname,
        status)
        values('Admin','Admin',curdate(),curdate(),'http://ftp.funet.fi/index/bible/fi/1992/Tit.html','Aktiivinen');

insert into  bookdatasource(
             created_by,updated_by,created_on,updated_on,  weburlname,
        status)
        values('Admin','Admin',curdate(),curdate(),'http://ftp.funet.fi/index/bible/fi/1992/2Tess.html','Aktiivinen');

insert into  bookdatasource(
             created_by,updated_by,created_on,updated_on,  weburlname,
        status)
        values('Admin','Admin',curdate(),curdate(),'http://ftp.funet.fi/index/bible/fi/1992/1Tim.html','Aktiivinen');

insert into  bookdatasource(
             created_by,updated_by,created_on,updated_on,  weburlname,
        status)
        values('Admin','Admin',curdate(),curdate(),'http://ftp.funet.fi/index/bible/fi/1992/2Tim.html','Aktiivinen');

insert into  bookdatasource(
             created_by,updated_by,created_on,updated_on,  weburlname,
        status)
        values('Admin','Admin',curdate(),curdate(),'http://ftp.funet.fi/index/bible/fi/1992/Tit.html','Aktiivinen');

insert into  bookdatasource(
             created_by,updated_by,created_on,updated_on,  weburlname,
        status)
        values('Admin','Admin',curdate(),curdate(),'http://ftp.funet.fi/index/bible/fi/1992/Filem.html','Aktiivinen');

insert into  bookdatasource(
             created_by,updated_by,created_on,updated_on,  weburlname,
        status)
        values('Admin','Admin',curdate(),curdate(),'http://ftp.funet.fi/index/bible/fi/1992/Hepr.html','Aktiivinen');

insert into  bookdatasource(
             created_by,updated_by,created_on,updated_on,  weburlname,
        status)
        values('Admin','Admin',curdate(),curdate(),'http://ftp.funet.fi/index/bible/fi/1992/Jaak.html','Aktiivinen');

insert into  bookdatasource(
             created_by,updated_by,created_on,updated_on,  weburlname,
        status)
        values('Admin','Admin',curdate(),curdate(),'http://ftp.funet.fi/index/bible/fi/1992/1Piet.html','Aktiivinen');

insert into  bookdatasource(
             created_by,updated_by,created_on,updated_on,  weburlname,
        status)
        values('Admin','Admin',curdate(),curdate(),'http://ftp.funet.fi/index/bible/fi/1992/2Piet.html','Aktiivinen');

insert into  bookdatasource(
             created_by,updated_by,created_on,updated_on,  weburlname,
        status)
        values('Admin','Admin',curdate(),curdate(),'http://ftp.funet.fi/index/bible/fi/1992/1Joh.html','Aktiivinen');

insert into  bookdatasource(
             created_by,updated_by,created_on,updated_on,  weburlname,
        status)
        values('Admin','Admin',curdate(),curdate(),'http://ftp.funet.fi/index/bible/fi/1992/2Joh.html','Aktiivinen');

insert into  bookdatasource(
             created_by,updated_by,created_on,updated_on,  weburlname,
        status)
        values('Admin','Admin',curdate(),curdate(),'http://ftp.funet.fi/index/bible/fi/1992/3Joh.html','Aktiivinen');

insert into  bookdatasource(
             created_by,updated_by,created_on,updated_on,  weburlname,
        status)
        values('Admin','Admin',curdate(),curdate(),'http://ftp.funet.fi/index/bible/fi/1992/Juud.html','Aktiivinen');

insert into  bookdatasource(
             created_by,updated_by,created_on,updated_on,  weburlname,
        status)
        values('Admin','Admin',curdate(),curdate(),'http://ftp.funet.fi/index/bible/fi/1992/Ilm.html','Aktiivinen');










insert into  book (created_by ,
        created_on,
        updated_by ,
        updated_on,
        book_text , fk_section_id,fk_bookdatasource_id)
 values('Admin',curdate(),'Admin',curdate(),'Evankeliumi Matteuksen mukaan',3,1);

insert into  book (created_by ,
        created_on,
        updated_by ,
        updated_on,
        book_text , fk_section_id,fk_bookdatasource_id)
 values('Admin',curdate(),'Admin',curdate(),'Evankeliumi Markuksen mukaan',3,2);

insert into  book (created_by ,
        created_on,
        updated_by ,
        updated_on,
        book_text , fk_section_id,fk_bookdatasource_id)
 values('Admin',curdate(),'Admin',curdate(),'Evankeliumi Luukaksen mukaan',3,3);


insert into  book (created_by ,
        created_on,
        updated_by ,
        updated_on,
        book_text , fk_section_id,fk_bookdatasource_id)
 values('Admin',curdate(),'Admin',curdate(),'Evankeliumi Johanneksen mukaan',3,4);

insert into  book (created_by ,
        created_on,
        updated_by ,
        updated_on,
        book_text , fk_section_id,fk_bookdatasource_id)
 values('Admin',curdate(),'Admin',curdate(),'Apostolien teot',3,5);

insert into  book (created_by ,
        created_on,
        updated_by ,
        updated_on,
        book_text , fk_section_id,fk_bookdatasource_id)
 values('Admin',curdate(),'Admin',curdate(),'Kirje Roomalaisille',3,6);

insert into  book (created_by ,
        created_on,
        updated_by ,
        updated_on,
        book_text , fk_section_id,fk_bookdatasource_id)
 values('Admin',curdate(),'Admin',curdate(),'Ensimmäinen kirje korinttilaisille',3,7);

insert into  book (created_by ,
        created_on,
        updated_by ,
        updated_on,
        book_text , fk_section_id,fk_bookdatasource_id)
 values('Admin',curdate(),'Admin',curdate(),'Toinen kirje korinttilaisille',3,8);

insert into  book (created_by ,
        created_on,
        updated_by ,
        updated_on,
        book_text , fk_section_id,fk_bookdatasource_id)
 values('Admin',curdate(),'Admin',curdate(),'Kirje galataltaisille',3,9);
insert into  book (created_by ,
        created_on,
        updated_by ,
        updated_on,
        book_text , fk_section_id,fk_bookdatasource_id)
 values('Admin',curdate(),'Admin',curdate(),'Kirje efesolaisille',3,10);

insert into  book (created_by ,
        created_on,
        updated_by ,
        updated_on,
        book_text , fk_section_id,fk_bookdatasource_id)
 values('Admin',curdate(),'Admin',curdate(),'Kirje filippiläisille',3,11);

insert into  book (created_by ,
        created_on,
        updated_by ,
        updated_on,
        book_text , fk_section_id,fk_bookdatasource_id)
 values('Admin',curdate(),'Admin',curdate(),'Kirje kolossalaisille',3,12);

insert into  book (created_by ,
        created_on,
        updated_by ,
        updated_on,
        book_text , fk_section_id,fk_bookdatasource_id)
 values('Admin',curdate(),'Admin',curdate(),'1. Kirje tessalonikalaisille',3,13);

insert into  book (created_by ,
        created_on,
        updated_by ,
        updated_on,
        book_text , fk_section_id,fk_bookdatasource_id)
 values('Admin',curdate(),'Admin',curdate(),'2. Kirje tessalonikalaisille',3,14);

insert into  book (created_by ,
        created_on,
        updated_by ,
        updated_on,
        book_text , fk_section_id,fk_bookdatasource_id)
 values('Admin',curdate(),'Admin',curdate(),'1. Kirje Timoteukselle',3,15);

insert into  book (created_by ,
        created_on,
        updated_by ,
        updated_on,
        book_text , fk_section_id,fk_bookdatasource_id)
 values('Admin',curdate(),'Admin',curdate(),'2. Kirje Timoteukselle',3,16);

insert into  book (created_by ,
        created_on,
        updated_by ,
        updated_on,
        book_text , fk_section_id,fk_bookdatasource_id)
 values('Admin',curdate(),'Admin',curdate(),'Kirje Titukselle',3,17);

insert into  book (created_by ,
        created_on,
        updated_by ,
        updated_on,
        book_text , fk_section_id,fk_bookdatasource_id)
 values('Admin',curdate(),'Admin',curdate(),'Kirje Filemonille',3,18);


insert into  book (created_by ,
        created_on,
        updated_by ,
        updated_on,
        book_text , fk_section_id,fk_bookdatasource_id)
 values('Admin',curdate(),'Admin',curdate(),'Kirje heprealaisille',3,19);

insert into  book (created_by ,
        created_on,
        updated_by ,
        updated_on,
        book_text , fk_section_id,fk_bookdatasource_id)
 values('Admin',curdate(),'Admin',curdate(),'Jaakobin kirje',3,20);

insert into  book (created_by ,
        created_on,
        updated_by ,
        updated_on,
        book_text , fk_section_id,fk_bookdatasource_id)
 values('Admin',curdate(),'Admin',curdate(),'1 Pietarin kirje',3,21);

insert into  book (created_by ,
        created_on,
        updated_by ,
        updated_on,
        book_text , fk_section_id,fk_bookdatasource_id)
 values('Admin',curdate(),'Admin',curdate(),'2 Pietarin kirje',3,22);

insert into  book (created_by ,
        created_on,
        updated_by ,
        updated_on,
        book_text , fk_section_id,fk_bookdatasource_id)
 values('Admin',curdate(),'Admin',curdate(),'1 Johanneksen kirje',3,23);

insert into  book (created_by ,
        created_on,
        updated_by ,
        updated_on,
        book_text , fk_section_id,fk_bookdatasource_id)
 values('Admin',curdate(),'Admin',curdate(),'2 Johanneksen kirje',3,24);

insert into  book (created_by ,
        created_on,
        updated_by ,
        updated_on,
        book_text , fk_section_id,fk_bookdatasource_id)
 values('Admin',curdate(),'Admin',curdate(),'3 Johanneksen kirje',3,25);


insert into  book (created_by ,
        created_on,
        updated_by ,
        updated_on,
        book_text , fk_section_id,fk_bookdatasource_id)
 values('Admin',curdate(),'Admin',curdate(),'Juudaksen kirje',3,26);

insert into  book (created_by ,
        created_on,
        updated_by ,
        updated_on,
        book_text , fk_section_id,fk_bookdatasource_id)
 values('Admin',curdate(),'Admin',curdate(),'Johanneksen ilmestys',3,27);







