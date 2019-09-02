package facades;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import entities.Movie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import utils.EMF_Creator;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class MovieFacade {

    private static MovieFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private MovieFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MovieFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public List<Movie> getMovieByTitle(String title){
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery q = em.createQuery("select m from Movie m where m.title = :title", Movie.class).setParameter("title", title);
            return q.getResultList();
        }finally{
            em.close();
        }
    }
    
    public List<Movie> getMovieByReleaseYear(int releaseYear){
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery q = em.createQuery("select m from Movie m where m.releaseYear = :releaseYear", Movie.class).setParameter("releaseYear", releaseYear);
            return q.getResultList();
        }finally{
            em.close();
        }     
    }
    
    public Movie addMovie(String title, int releaseYear){
        Movie m = new Movie(title, releaseYear);
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(m);
            em.getTransaction().commit();
            return m;
        }finally{
            em.close();
        }
    }
    
    public List<Movie> getAllMovies(){
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery q = em.createQuery("select m from Movie m", Movie.class);
            return q.getResultList();
        }finally{
            em.close();
        }
    }
    

}
