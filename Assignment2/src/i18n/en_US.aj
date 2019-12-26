package i18n;

public aspect en_US {
	before() : execution(* *.main(..)){
		I18N.setInstance(new I18N("en","US"));
		System.out.println("This product is in english.");
	}
}
