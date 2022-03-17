DROP TABLE IF EXISTS `mismatch_analysis`.`operation`;
DROP TABLE IF EXISTS `mismatch_analysis`.`system`;

CREATE TABLE `mismatch_analysis`.`system` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `system_name` VARCHAR(100) NOT NULL,
  `sc_path` 	VARCHAR(150) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `mismatch_analysis`.`operation` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `system_id` VARCHAR(100) NOT NULL,
  `operation_id` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`));

-- ********************************************************************************************************************************************

-- Contract Description Language SCs
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-controller-1', '/mismatchAnalysis/interfaceDescription/cdl/cdl_consumer.xml');
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-sensor-1', '/mismatchAnalysis/interfaceDescription/cdl/cdl_provider1.xml');
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-sensor-2', '/mismatchAnalysis/interfaceDescription/cdl/cdl_provider2.xml');
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-sensor-3', '/mismatchAnalysis/interfaceDescription/cdl/cdl_provider3.xml');
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-sensor-4', '/mismatchAnalysis/interfaceDescription/cdl/cdl_provider4.xml');
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-sensor-5', '/mismatchAnalysis/interfaceDescription/cdl/cdl_provider5.xml');
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-sensor-6', '/mismatchAnalysis/interfaceDescription/cdl/cdl_provider6.xml');
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-sensor-7', '/mismatchAnalysis/interfaceDescription/cdl/cdl_provider7.xml');
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-sensor-8', '/mismatchAnalysis/interfaceDescription/cdl/cdl_provider8.xml');

INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('1', 'get-temperature');
INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('2', 'get-temperature');
INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('3', 'get-temperature');
INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('4', 'get-temperature');
INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('5', 'get-temperature');
INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('6', 'get-temperature');
INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('7', 'get-temperature');
INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('8', 'get-temperature');
INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('9', 'get-temperature');

-- ********************************************************************************************************************************************

-- OpenAPI SCs
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-controller-2', '/mismatchAnalysis/interfaceDescription/openapi/openapi_consumer.yaml');
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-sensor-9', '/mismatchAnalysis/interfaceDescription/openapi/openapi_provider1.yaml');
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-sensor-10', '/mismatchAnalysis/interfaceDescription/openapi/openapi_provider2.yaml');
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-sensor-11', '/mismatchAnalysis/interfaceDescription/openapi/openapi_provider3.yaml');
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-sensor-12', '/mismatchAnalysis/interfaceDescription/openapi/openapi_provider4.yaml');

INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('10', 'get-temperature');
INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('11', 'get-temperature');
INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('12', 'get-temperature');
INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('13', 'get-temperature');
INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('14', 'get-temperature');

-- ********************************************************************************************************************************************

-- Test SCs
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-test-controller-1', '/mismatchAnalysis/interfaceDescription/test/cdl_consumer_test1.xml');
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-test-sensor-1', '/mismatchAnalysis/interfaceDescription/test/cdl_provider_test1.xml');

INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('15', 'get-temperature');
INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('16', 'get-temperature');

-- ********************************************************************************************************************************************


INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-test-controller-2', '/mismatchAnalysis/interfaceDescription/test/cdl_consumer_test2.xml');
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-test-sensor-2', '/mismatchAnalysis/interfaceDescription/test/cdl_provider_test2.xml');

INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('17', 'get-temperature');
INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('18', 'get-temperature');

-- ********************************************************************************************************************************************

INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-test-controller-3', '/mismatchAnalysis/interfaceDescription/test/cdl_consumer_test3.xml');
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-test-sensor-3', '/mismatchAnalysis/interfaceDescription/test/cdl_provider_test3.xml');

INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('19', 'get-temperature');
INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('20', 'get-temperature');

-- ********************************************************************************************************************************************

INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-test-controller-4', '/mismatchAnalysis/interfaceDescription/test/cdl_consumer_test4.xml');
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-test-sensor-4', '/mismatchAnalysis/interfaceDescription/test/cdl_provider_test4.xml');

INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('21', 'get-temperature');
INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('22', 'get-temperature');

-- ********************************************************************************************************************************************

INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-test-controller-5', '/mismatchAnalysis/interfaceDescription/test/cdl_consumer_test5.xml');
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-test-sensor-5', '/mismatchAnalysis/interfaceDescription/test/cdl_provider_test5.xml');
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-test-sensor-5b', '/mismatchAnalysis/interfaceDescription/test/cdl_provider_test5b.xml');

INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('23', 'get-temperature');
INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('24', 'get-temperature');
INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('25', 'get-temperature');

-- ********************************************************************************************************************************************

INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-test-controller-6', '/mismatchAnalysis/interfaceDescription/test/cdl_consumer_test6.xml');
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-test-sensor-6', '/mismatchAnalysis/interfaceDescription/test/cdl_provider_test6.xml');
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-test-sensor-6b', '/mismatchAnalysis/interfaceDescription/test/cdl_provider_test6b.xml');

INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('26', 'get-temperature');
INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('27', 'get-temperature');
INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('28', 'get-temperature');

-- ********************************************************************************************************************************************

INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-test-controller-7', '/mismatchAnalysis/interfaceDescription/test/cdl_consumer_test7.xml');
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-test-sensor-7', '/mismatchAnalysis/interfaceDescription/test/cdl_provider_test7.xml');

INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('29', 'get-temperature');
INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('30', 'get-temperature');

-- ********************************************************************************************************************************************

INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-test-controller-A', '/mismatchAnalysis/interfaceDescription/test/cdl_consumer_A.xml');
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-test-sensor-A1', '/mismatchAnalysis/interfaceDescription/test/cdl_provider_A1.xml');
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-test-sensor-A2', '/mismatchAnalysis/interfaceDescription/test/cdl_provider_A2.xml');
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-test-sensor-A3', '/mismatchAnalysis/interfaceDescription/test/cdl_provider_A3.xml');
INSERT INTO `mismatch_analysis`.`system` (`system_name`, `sc_path`) VALUES ('temperature-test-sensor-A4', '/mismatchAnalysis/interfaceDescription/test/cdl_provider_A4.xml');

INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('31', 'get-temperature');
INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('32', 'get-temperature');
INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('33', 'get-temperature');
INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('34', 'get-temperature');
INSERT INTO `mismatch_analysis`.`operation` (`system_id`, `operation_id`) VALUES ('35', 'get-temperature');