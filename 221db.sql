
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


CREATE SCHEMA IF NOT EXISTS `dbkp5` DEFAULT CHARACTER SET utf8 ;
USE `dbkp5` ;


DROP TABLE IF EXISTS `dbkp5`.`event` ;

CREATE TABLE IF NOT EXISTS `dbkp5`.`event` (
  `bookingId` INT NOT NULL,
  `eventStartTime` DATETIME NOT NULL,
  `bookingName` VARCHAR(100) NOT NULL,
  `bookingEmail` VARCHAR(50) NOT NULL,
  `eventNotes` VARCHAR(500) NULL,
  `eventCategory` INT NOT NULL,
  PRIMARY KEY (`bookingId`),
  UNIQUE INDEX `eventStartTime_UNIQUE` (`eventStartTime` ASC) VISIBLE,
  INDEX `fk_event_eventCategory_idx` (`eventCategory` ASC) VISIBLE,
  CONSTRAINT `fk_event_eventCategory`
    FOREIGN KEY (`eventCategory`)
    REFERENCES `dbkp5`.`eventCategory` (`categoryId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



DROP TABLE IF EXISTS `dbkp5`.`eventCategory` ;

CREATE TABLE IF NOT EXISTS `dbkp5`.`eventCategory` (
  `categoryId` INT NOT NULL,
  `eventCategoryName` VARCHAR(100) NOT NULL,
  `eventCategoryDescription` VARCHAR(500) NULL,
  `eventDuration` TIME NOT NULL,
  PRIMARY KEY (`categoryId`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
