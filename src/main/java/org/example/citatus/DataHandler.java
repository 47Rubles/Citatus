package org.example.citatus;



import java.util.Arrays;
import java.util.Objects;

public class DataHandler {

    public  static  Citation addCitation(Citation citation, CitationRepository repository) {
//        System.out.println("Цитата: " + citation.getText());
//        System.out.println("Автор: " + citation.getAuthor());
        return repository.save(citation);
    }

//    private static Citation getCitationDB(Long id) {
//        Citation[] cits = {
//            new Citation("Тугарин Змей", "ТУДА НАМ НАДО!!!", 1L),
//            new Citation("Winston Churchill", "Underidoderidoderidoderidoo", 2L),
//            new Citation("Неизвестность", "Non so cosa mi riserva il domani… L’importante e essere felice ogg", 3L)
//        };
//        Citation citation = null;
//        for (Citation c : cits) {
//            if (c.getId().equals(id)) {
//                citation = c;
//                break;
//            }
//        }
//        return citation;
//    }

private static Citation getCitationDB(Long id, CitationRepository repository) {

    return repository.findById(id).orElseThrow();
}

   public static Citation getCitation(Long id, CitationRepository repository) {
        Citation citation = getCitationDB(id, repository);
        return citation;
   }

   public static Citation translateCitation(Long id, String lang, CitationRepository repository) {
        Citation citation = getCitation(id, repository);
        String translated = AITranslationGenerationUnit.translateText(citation.getText(), lang);

        return new Citation(citation.getAuthor(), translated);
   }

   public static Citation generateCitation() {

        return new Citation("Сгенерированно ИИ", AITranslationGenerationUnit.generateText());
   }

   public static void deleteCitation(Long id, CitationRepository repository) {
        repository.deleteById(id);
   }
}