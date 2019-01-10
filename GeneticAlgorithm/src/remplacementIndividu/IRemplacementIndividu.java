package remplacementIndividu;

import modeles.Individu;
import modeles.Population;

public interface IRemplacementIndividu {
	
	
	Individu getIndividuARemplacer(Population pop);
}
