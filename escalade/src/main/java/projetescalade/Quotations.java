package projetescalade;

import java.util.ArrayList;

import javabeans.Voie;

public class Quotations {
	private static String[] alphabet = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
	//
	//
	public static String[] getMinMax(ArrayList<Voie> voiesInput){
		String[] minMax = {"9c+","0a"};//9c+
		//
		if(voiesInput==null) {
			return null;
		}else if(voiesInput.isEmpty()) {
			return null;
		}
		for(Voie v:voiesInput) {
			if(v.getQuotationVoie().length()>3) {
				String quotations[]=v.getQuotationVoie().split("/");
				for(String quot:quotations) {
					if(indice(quot)<indice(minMax[0])) {
						minMax[0]=quot;
					}else if(indice(quot)==indice(minMax[0])) {
						int valeurcomparee3=comparateur(quot,minMax[0]);
						if(valeurcomparee3==-1) {
							minMax[0]=quot;
						}else if(valeurcomparee3==0) {
							if(minMax[0].length()>2 && quot.length()<3) {
								if(minMax[0].substring(2).equals("+")) {
									minMax[0]=quot;
								}
							}
						}
					}
					if(indice(quot)>indice(minMax[1])) {
						minMax[1]=quot;
					}else if(indice(quot)==indice(minMax[1])) {
						int valeurcomparee4=comparateur(quot,minMax[1]);
						if(valeurcomparee4==1) {
							minMax[1]=quot;
						}else if(valeurcomparee4==0) {
							if(quot.length()>2 && minMax[1].length()<3) {
								if(quot.substring(2).equals("+")) {
									minMax[1]=quot;
								}
							}
						}
					}
				}
			}else {
				if(indice(v.getQuotationVoie())<indice(minMax[0])) {
					minMax[0]=v.getQuotationVoie();
				}else if(indice(v.getQuotationVoie())==indice(minMax[0])) {
					int valeurcomparee=comparateur(v.getQuotationVoie(),minMax[0]);
					if(valeurcomparee==-1) {
						minMax[0]=v.getQuotationVoie();
					}else if(valeurcomparee==0) {
						if(minMax[0].length()>2 && v.getQuotationVoie().length()<3) {
							if(minMax[0].substring(2).equals("+")) {
								minMax[0]=v.getQuotationVoie();
							}
						}
					}
				}
				if(indice(v.getQuotationVoie())>indice(minMax[1])) {
					minMax[1]=v.getQuotationVoie();
				}else if(indice(v.getQuotationVoie())==indice(minMax[1])) {
					int valeurcomparee2=comparateur(v.getQuotationVoie(),minMax[1]);
					if(valeurcomparee2==1) {
						minMax[1]=v.getQuotationVoie();
					}else if(valeurcomparee2==0) {
						if(v.getQuotationVoie().length()>2 && minMax[1].length()<3) {
							if(v.getQuotationVoie().substring(2).equals("+")) {
								minMax[1]=v.getQuotationVoie();
							}
						}
					}
				}
			}
			
		}
		//
		return minMax;
	}
	//
	private static int indice(String quotation) {
		int indiceOutput=Integer.valueOf(quotation.substring(0, 1));
		return indiceOutput;
	}
	//
	private static int comparateur(String quotation, String ref) {
		String indiceSecondaire=quotation.substring(1, 2);
		String reference=ref.substring(1, 2);
		int comparaison;
		int i;
		int i1=0;
		int i2=0;
		for( i=0; i<3; i++) {
			if(indiceSecondaire.equals(alphabet[i])) {
				i1=i;
			}
			if(reference.equals(alphabet[i])) {
				i2=i;
			}
		}
		if(i1<i2) {
			comparaison=-1;
		}else if(i1==i2) {
			comparaison=0;
		}else {
			comparaison=1;
		}
		return comparaison;
	}
	//
	//
	public static ArrayList<String> getListCotation(Voie voieInput){
		ArrayList<String> listeDesCotations=new ArrayList<String>();
		if(voieInput.getQuotationVoie().length()>3) {
			String quotations[]=voieInput.getQuotationVoie().split("/");
			for(String cot:quotations) {
				listeDesCotations.add(cot);
			}
		}else {
			listeDesCotations.add(voieInput.getQuotationVoie());
		}	
		return listeDesCotations;
	}
	//
	//
	//
	//
	//Une méthode simple pour résoudre ce problème serait d'utiliser un ArrayList listant toutes les notes possibles,
	//le résultat serait alors obtenu en comparant les indices numériques de l'ArrayList grâce à .indexOf.
	//Voici la méthode complexe:
	public static ArrayList<Voie> getVoiesCorrespondantesAuxCotations(ArrayList<Voie> voiesInput, String cotMin, String cotMax){
		//
		if(indice(cotMax)<indice(cotMin)) {
			//problème
			cotMin="1a";
			cotMax="9c+";
		}else if(indice(cotMax)==indice(cotMin)) {
			if(comparateur(cotMax,cotMin)==-1) {
				//problème
				cotMin="1a";
				cotMax="9c+";
			}else if(comparateur(cotMax,cotMin)==0) {
				if(cotMax.length()<cotMin.length()) {
					//problème
					cotMin="1a";
					cotMax="9c+";
				}
			}
		}
		//
		String[] minMax = {cotMin,cotMax};//
		//
		ArrayList<Voie> voiesDontLesCotationsMatch = new ArrayList<Voie>();
		//
		//
		for(Voie v:voiesInput) {
		if(v.getQuotationVoie()!=null) {
			if(v.getQuotationVoie().length()>3) {
				String quotations[]=v.getQuotationVoie().split("/");
				boolean ok=false;
				for(String quot:quotations) {
						if(indice(quot)>indice(minMax[0])) {
							//
							if(indice(quot)<indice(minMax[1])) {
								//
								//Jackpot!
								if(ok==false) {
									voiesDontLesCotationsMatch.add(v);
								}
								ok=true;
								//
							}else if(indice(quot)==indice(minMax[1])) {
								//
								int valeurcomparee=comparateur(quot,minMax[1]);
								if(valeurcomparee==-1) {
									//
									//Jackpot!
									if(ok==false) {
										voiesDontLesCotationsMatch.add(v);
									}
									ok=true;
									//
								}else if(valeurcomparee==0) {
									if((minMax[1].length()>2 && quot.length()<3)||(minMax[1].length()==quot.length())) {
										//
										//Jackpot!
										if(ok==false) {
											voiesDontLesCotationsMatch.add(v);
										}
										ok=true;
										//
									}
								}
							}
						}else if(indice(quot)==indice(minMax[0])) {
							int valeurcomparee=comparateur(quot,minMax[0]);
							if(valeurcomparee==1) {
								if(indice(quot)<indice(minMax[1])) {
									//
									//Jackpot!
									if(ok==false) {
										voiesDontLesCotationsMatch.add(v);
									}
									ok=true;
									//
								}else if(indice(quot)==indice(minMax[1])) {
									//
									int valeurcomparee2=comparateur(quot,minMax[1]);
									if(valeurcomparee2==-1) {
										//
										//Jackpot!
										if(ok==false) {
											voiesDontLesCotationsMatch.add(v);
										}
										ok=true;
										//
									}else if(valeurcomparee2==0) {
										if((minMax[1].length()>2 && quot.length()<3)||(minMax[1].length()==quot.length())) {
											//
											//Jackpot!
											if(ok==false) {
												voiesDontLesCotationsMatch.add(v);
											}
											ok=true;
											//
										}
									}
								}
							}else if(valeurcomparee==0) {
								if((minMax[0].length()<3 && quot.length()>2)||(minMax[0].length()==quot.length())) {
									if(indice(quot)<indice(minMax[1])) {
										//
										//Jackpot!
										if(ok==false) {
											voiesDontLesCotationsMatch.add(v);
										}
										ok=true;
										//
									}else if(indice(quot)==indice(minMax[1])) {
										//
										int valeurcomparee2=comparateur(quot,minMax[1]);
										if(valeurcomparee2==-1) {
											//
											//Jackpot!
											if(ok==false) {
												voiesDontLesCotationsMatch.add(v);
											}
											ok=true;
											//
										}else if(valeurcomparee2==0) {
											if((minMax[1].length()>2 && quot.length()<3)||(minMax[1].length()==quot.length())) {
												//
												//Jackpot!
												if(ok==false) {
													voiesDontLesCotationsMatch.add(v);
												}
												ok=true;
												//
											}
										}
									}
								}
							}
						}
				}
			}else {
				if(indice(v.getQuotationVoie())>indice(minMax[0])) {
					//
					if(indice(v.getQuotationVoie())<indice(minMax[1])) {
						//
						//Jackpot!
						voiesDontLesCotationsMatch.add(v);
						//
					}else if(indice(v.getQuotationVoie())==indice(minMax[1])) {
						//
						int valeurcomparee=comparateur(v.getQuotationVoie(),minMax[1]);
						if(valeurcomparee==-1) {
							//
							//Jackpot!
							voiesDontLesCotationsMatch.add(v);
							//
						}else if(valeurcomparee==0) {
							if((minMax[1].length()>2 && v.getQuotationVoie().length()<3)||(minMax[1].length()==v.getQuotationVoie().length())) {
								//
								//Jackpot!
								voiesDontLesCotationsMatch.add(v);
								//
							}
						}
					}
				}else if(indice(v.getQuotationVoie())==indice(minMax[0])) {
					int valeurcomparee=comparateur(v.getQuotationVoie(),minMax[0]);
					if(valeurcomparee==1) {
						if(indice(v.getQuotationVoie())<indice(minMax[1])) {
							//
							//Jackpot!
							voiesDontLesCotationsMatch.add(v);
							//
						}else if(indice(v.getQuotationVoie())==indice(minMax[1])) {
							//
							int valeurcomparee2=comparateur(v.getQuotationVoie(),minMax[1]);
							if(valeurcomparee2==-1) {
								//
								//Jackpot!
								voiesDontLesCotationsMatch.add(v);
								//
							}else if(valeurcomparee2==0) {
								if((minMax[1].length()>2 && v.getQuotationVoie().length()<3)||(minMax[1].length()==v.getQuotationVoie().length())) {
									//
									//Jackpot!
									voiesDontLesCotationsMatch.add(v);
									//
								}
							}
						}
					}else if(valeurcomparee==0) {
						if((minMax[0].length()<3 && v.getQuotationVoie().length()>2)||(minMax[0].length()==v.getQuotationVoie().length())) {
							if(indice(v.getQuotationVoie())<indice(minMax[1])) {
								//
								//Jackpot!
								voiesDontLesCotationsMatch.add(v);
								//
							}else if(indice(v.getQuotationVoie())==indice(minMax[1])) {
								//
								int valeurcomparee2=comparateur(v.getQuotationVoie(),minMax[1]);
								if(valeurcomparee2==-1) {
									//
									//Jackpot!
									voiesDontLesCotationsMatch.add(v);
									//
								}else if(valeurcomparee2==0) {
									if((minMax[1].length()>2 && v.getQuotationVoie().length()<3)||(minMax[1].length()==v.getQuotationVoie().length())) {
										//
										//Jackpot!
										voiesDontLesCotationsMatch.add(v);
										//
									}
								}
							}
						}
					}
				}
			}
		}	
		}
		//
		return voiesDontLesCotationsMatch;
	}
}
