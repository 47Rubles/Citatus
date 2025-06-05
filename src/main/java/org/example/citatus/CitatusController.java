package org.example.citatus;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Controller
@RequestMapping("/api")
public class CitatusController {
    private final WebClient webClient;
    private final CitationRepository citationRepository;

    public CitatusController(WebClient webClient, CitationRepository citationRepository) {
        this.webClient = webClient;
        this.citationRepository = citationRepository;
    }

    @GetMapping("/citatus")
    public String citatus(Model model) {
        return "index";
    }

    @PostMapping("/save")
    public String saveCitation(@RequestParam("text") String citationText, @RequestParam(value = "author", required = false) String citationAuthor, Model model) {

        Citation citation = null;

        if(citationAuthor == null) {
            citation = new Citation("Без автора", citationText);
        }
        else {
            citation = new Citation(citationAuthor, citationText);
        }
        DataHandler.addCitation(citation, citationRepository);

        return "redirect:/api/citatus";
    }

    @GetMapping("/citatus/view")
    public String viewCitatus(@RequestParam("id") Long id, Model model) {
        Citation citation = DataHandler.getCitation(id, citationRepository);
        model.addAttribute("citationResult", citation);

        return "index";
    }

    @PostMapping("/citatus/translate")
    public String translateCitation(@RequestParam("id") Long id, @RequestParam(value = "targetLang") String lang, Model model) {

        if(id == null) {
            System.out.println("id = null");
        }
        Citation translatedCitation = DataHandler.translateCitation(id, lang, citationRepository);
        model.addAttribute("citationResult", translatedCitation);
        return "index";
    }

    @PostMapping("/citatus/generate")
    public String generateCitation(Model model) {
        Citation generatetedCitation = DataHandler.generateCitation();
        model.addAttribute("citationResult", generatetedCitation);

        return "index";
    }

    @GetMapping("/citatus/all")
    public String viewAllCitations(Model model) {
        List<Citation> allCitations = citationRepository.findAll();
        model.addAttribute("allCitations", allCitations);

        return "all-quotes";
    }

    @PostMapping("/citatus/delete")
    public String deleteCitation(@RequestParam("id") Long id, Model model) {
        if (id == null) {
            model.addAttribute("errorMessage", "Пожалуйста, введите ID цитаты для удаления.");
        } else {
            boolean exists = citationRepository.existsById(id);

            if (exists) {
                DataHandler.deleteCitation(id, citationRepository);
                model.addAttribute("message", "Цитата удалена.");
            } else {
                model.addAttribute("errorMessage", "Цитата с ID " + id + " не найдена.");
            }
        }
        return "index";
    }
}
// автор цитаты (если не указать - "без автора")
//