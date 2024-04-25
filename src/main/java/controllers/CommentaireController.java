/*package controllers;

import entities.Commentaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import services.CommentaireService;

@Controller
public class CommentaireController {

    private final CommentaireService commentaireService;

    @Autowired
    public CommentaireController(CommentaireService commentaireService) {
        this.commentaireService = commentaireService;
    }

    @PostMapping("/ajoutCommentaire")
    public String ajoutCommentaire(@ModelAttribute("commentaire") Commentaire commentaire, Model model) {
        // Call service layer to add the comment to the database
        commentaireService.ajouterCommentaire(commentaire);
        return "redirect:/"; // Redirect to the homepage or wherever appropriate
    }
}
*/