var Config = {};
//Config.HOST = 'localhost:8084';
Config.HOST = 'labeling.i3mainz.hs-mainz.de';

Config.SERVER = 'http://' + Config.HOST + '/knowledgebase/';
Config.FILTER = Config.SERVER + "FilterData";
Config.INPUT = Config.SERVER + "Input";
Config.HINT = Config.SERVER + "Hint";
Config.MODIFY = Config.SERVER + "Modify";
Config.DELETE = Config.SERVER + "Delete";
Config.SPARQL = Config.SERVER + "sparql";
Config.RDF = Config.SERVER + "getRDF";
Config.CALC_PIE = Config.SERVER + "Calc?query=PIE";
Config.CALC_COLUMN = Config.SERVER + "Calc?query=COLUMN";
Config.CALC_LINE = Config.SERVER + "Calc?query=LINE";