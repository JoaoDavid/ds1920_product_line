package config.i18n;

public aspect pt_PT {
	before() : execution(* *.main(..)){
		I18N.setInstance(new I18N("pt","PT"));
		System.out.println("O idioma deste produto eh Portugues.");
	}
}
