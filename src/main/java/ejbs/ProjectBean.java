package ejbs;

import entities.Client;
import entities.Designer;
import entities.Project;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class ProjectBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(String name, Long idClient, Long idDesigner)
            throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {

        Project project = findProject(name);
        if (project != null) {
            throw new MyEntityExistsException(String.format("Project with name %s already exists", name));
        }

        Client client = entityManager.find(Client.class, idClient);
        if (client == null) {
            throw new MyEntityNotFoundException(String.format("%s", "asd"));
        }

        Designer designer = entityManager.find(Designer.class, idDesigner);
        if (designer == null){
            throw new MyEntityNotFoundException(String.format("%s", "nameDesigner"));
        }

        try {
            project = new Project(name, client, designer);
            client.addProject(project);
            designer.getProjects().add(project);
            entityManager.persist(project);
        } catch (ConstraintViolationException constraintViolationException){
            throw new MyConstraintViolationException(constraintViolationException);
        }

    }

    public List<Project> getAll(){
        return entityManager.createNamedQuery("getAllProjects").getResultList();
    }

    public Project findProject(String name) {
        return entityManager.find(Project.class, name);
    }

    public void delete(String name)
            throws MyEntityNotFoundException {

        Project project = findProject(name);

        if (project == null) {
            throw new MyEntityNotFoundException("ERROR");
        }
        project.getClient().getProjects().remove(project);
        project.getDesigner().getProjects().remove(project);
        entityManager.remove(project);
    }

    public void update(String name, Long idClient, Long idDesigner)
            throws MyEntityNotFoundException, MyConstraintViolationException {

        Project project = entityManager.find(Project.class, name);

        if (project == null)
            throw new MyEntityNotFoundException("ERROR");

        Client client = entityManager.find(Client.class, idClient);

        if (client == null)
            throw new MyEntityNotFoundException("");

        Designer designer = entityManager.find(Designer.class, idDesigner);

        if (designer == null)
            throw new MyEntityNotFoundException("");

        try {
//            entityManager.lock(project, LockModeType.OPTIMISTIC);
            project.setName(name);
            project.setClient(client);
            project.setDesigner(designer);
        } catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }
    }

    public void availableToClient(String name) throws MyEntityNotFoundException, MyConstraintViolationException {
        Project project = findProject(name);
        if (project == null)
            throw new MyEntityNotFoundException("");

        try {
            entityManager.lock(project, LockModeType.OPTIMISTIC);
            project.setAvailableToClient(true);
            for (Project proj : project.getClient().getProjects()
            ) {
                if (proj.getName().equals(project.getName())) {
                    entityManager.lock(proj, LockModeType.OPTIMISTIC);
                    proj.setAvailableToClient(true);
                }
            }
        } catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }
    }
}
