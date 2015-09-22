# Rule-based Approach

* it seem !infinitive phrase
(this last check is something that the author and I noticed would improve accuracy otherwise the rule would just be it seem)

```
    private boolean heuristicSeem(int id)
    {
        return tree.get(id).getHead().getLemma().equals("seem") && !hasPOSByTag(tree.get(id).getHead().getID(), POSTagEn.POS_TO);
    }

	private boolean hasPOSByTag(int i, String pos)
	{
		int size = tree.size();
		for (; i < size; i++) {
			if		(isAdverb.test(tree.get(i).getPOSTag())) continue;
			else if (tree.get(i).isPOSTag(pos)) return true;
			return false;
		}
		return false;
	}

	private final Predicate<String> isAdverb	= x -> POSLibEn.isAdverb(x);
```

* it be NUMBER

```
    private boolean findItBe(int id)
	{
		DEPNode node = tree.get(id);
		int i = node.getHead().getID(), size = tree.size();
		if (node.getHead().getLemma().equals("be")) {
			for (; i < size; i++) {
				node = tree.get(i);
				if 		(isAdverb.test(node.getPOSTag())) continue;
				else if (node.isLabel(DEPTagEn.DEP_NUMMOD)) return true;
				return false;
				}
			}
		return false;
	}
```

* it be [time_words]

```
    private boolean findItBe(int id)
	{
		DEPNode node = tree.get(id);
		int i = node.getHead().getID(), size = tree.size();
		if (node.getHead().getLemma().equals("be")) {
			for (; i < size; i++) {
				node = tree.get(i);
				if 		(isAdverb.test(node.getPOSTag())) continue;
				else if (timeWords.contains(node.getLemma())) return true;
				return false;
				}
			}
		return false;
	}
```

* it be [weather word]

```
    private boolean findItBe(int id)
	{
		DEPNode node = tree.get(id);
		int i = node.getHead().getID(), size = tree.size();
		if (node.getHead().getLemma().equals("be")) {
			for (; i < size; i++) {
				node = tree.get(i);
				if 		(isAdverb.test(node.getPOSTag())) continue;
				else if (weatherTerms.contains(node.getLemma())) return true;
				return false;
				}
			}
		return false;
	}
```

* it be [season]

```
    private boolean findItBe(int id)
	{
		DEPNode node = tree.get(id);
		int i = node.getHead().getID(), size = tree.size();
		if (node.getHead().getLemma().equals("be")) {
			for (; i < size; i++) {
				node = tree.get(i);
				if 		(isAdverb.test(node.getPOSTag())) continue;
				else if (seasons.contains(node.getLemma())) return true;
				return false;
				}
			}
		return false;
	}
```

* it be ADJ that

```
    private boolean findItBe(int id)
	{
		DEPNode node = tree.get(id);
		int i = node.getHead().getID(), size = tree.size();
		if (node.getHead().getLemma().equals("be")) {
			for (; i < size; i++) {
				node = tree.get(i);
				if 		(isAdverb.test(node.getPOSTag())) continue;
				else if (isAdjective.test(node.getPOSTag())) return itBeAdj(++i);
				return false;
				}
			}
		return false;
	}

	private final Predicate<String> isAdjective	= x -> POSLibEn.isAdjective(x);

    private boolean itBeAdj(int i)
    {
        return hasWord(i, "that");
    }

	private boolean hasWord(int i, String word)
	{
		int size = tree.size();
		for (; i < size; i++) {
			DEPNode node = tree.get(i);
			if (isAdverb.test(node.getPOSTag())) continue;
			else if (node.getLemma().equals(word)) return true;
			return false;
		}
		return false;
	}
```

* it be ADJ for N to V

```
	private boolean findItBe(int id)
	{
		DEPNode node = tree.get(id);
		int i = node.getHead().getID(), size = tree.size();
		if (node.getHead().getLemma().equals("be")) {
			for (; i < size; i++) {
				node = tree.get(i);
				if 		(isAdverb.test(node.getPOSTag())) continue;
				else if (isAdjective.test(node.getPOSTag())) return itBeAdj(++i);
				return false;
				}
			}
		return false;
	}

	private boolean itBeAdj(int i)
   	{
   	    forTo(i);
    }

    private boolean forTo(int i)
    {
        i = findWordWithPOSTag(i, "for", POSTagEn.POS_IN);
        return (i < tree.size()) ?  hasPOSByTag(i, POSTagEn.POS_TO) : false;
    }

    private int findWordWithPOSTag(int i, String word, String pos)
    {
        int size = tree.size();
        for (; i < size; i++) {
            DEPNode node = tree.get(i);
            if (isAdverb.test(node.getPOSTag())) continue;
            else if (node.getLemma().equals(word) && node.isPOSTag(pos)) return i;
            return size;
        }
        return size;
    }
```

* it be [past tense cognitive verb] that

```
	private boolean pastCogVerb(int id)
	{
		int size = tree.size();
		int i = findWord(id+1, "be");
		i++;
		for (; i < size; i++) {
			DEPNode node = tree.get(i);
			if (isAdverb.test(node.getPOSTag())) continue;
			else if(node.isPOSTag("VBD") || node.isPOSTag("VBN") && cognitiveVerbs.contains(node.getLemma())) return hasWord(node.getID()+1, "that");
			return false;
		}
		return false;
	}
```

* it be ADJ PRONOUN

```
	private boolean findItBe(int id)
	{
		DEPNode node = tree.get(id);
		int i = node.getHead().getID(), size = tree.size();
		if (node.getHead().getLemma().equals("be")) {
			for (; i < size; i++) {
				node = tree.get(i);
				if 		(isAdverb.test(node.getPOSTag())) continue;
				else if (isAdjective.test(node.getPOSTag())) return itBeAdj(++i);
				return false;
				}
			}
		return false;
	}

		private boolean itBeAdj(int i)
    	{
    		hasPOS(i, isPronoun);
    	}
```

* N V it ADJ for N to V

```
	private boolean specialConstruct(int id)
	{
		int i = findPOS(1, isNoun);
		i = findPOS(++i, isVerb);
		i = findWord(++i, "it");
		i = findPOS(++i, isAdjective);
		return i < tree.size();
	}

	private final Predicate<String> isNoun		= x -> POSLibEn.isNoun(x);
    private final Predicate<String> isVerb		= x -> POSLibEn.isVerb(x);

    private int findPOS(int i, Predicate<String> f1)
    {
        int size = tree.size();
        for (; i < size; i++) {
            String pos = tree.get(i).getPOSTag();
            if (isAdverb.test(pos)) continue;
            else if (f1.test(pos)) return i;
            return size;
        }
        return size;
    }

    private int findWord(int i, String word)
    {
        int size = tree.size();
        for (; i < size; i++) {
            DEPNode node = tree.get(i);
            if (isAdverb.test(node.getPOSTag())) continue;
            if (node.getLemma().equals(word)) return i;
            return size;
        }
        return size;
    }
```

* it is DET [cognitive noun] that

OR

* it is on DET [cognitive noun] that

```
private boolean cognitiveNounPattern(int id)	
	{
		DEPNode node = tree.get(id);
		return node.getHead().getWordForm().equals("is") && cogSplit(node.getHead().getID()+1);
	}
	
	private boolean cogSplit(int i)	
	{
		int answer = findWord(i, "on");
		answer = findPOSByTag(++answer, POSTagEn.POS_DT);
		if (answer < tree.size()) return cogEnd(answer);
		else { answer = findPOSByTag(i+1, POSTagEn.POS_DT);
		return cogEnd(answer);
	}
		
	private boolean cogEnd(int i)
	{
		i = findContainsWord(++i, cognitiveNouns);
		return hasWord(++i, "that");
	}
```

# Rule-based Approach

3. re-annotate everything using the multimethod
	- add more examples to guidelines
4. begin writing paper in latex
5. look into mechanical turk