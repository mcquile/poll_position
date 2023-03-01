package org.example.restrictions;

import org.example.branches.BranchRepository;
import org.example.branches.models.Branch;
import org.example.polls.models.Poll;
import org.example.restrictions.models.SpecificUserRestriction;
import org.example.restrictions.models.UserRestriction;
import org.example.restrictions.models.dto.SpecificUserRestrictionDTO;
import org.example.restrictions.models.dto.UserRestrictionDTO;
import org.example.sexes.SexRepository;
import org.example.sexes.models.Sex;
import org.example.users.UserRepository;
import org.example.users.UserService;
import org.example.users.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Optional;
import java.util.regex.Pattern;

public class RestrictionsService {

    public static boolean userMatchesGenericRestriction(User user, UserRestriction genericRestriction) {
        Branch branchRestriction = genericRestriction.getBranchRestriction();
        Sex sexRestriction = genericRestriction.getSexRestrictedTo();
        String firstNamePatternRestriction = genericRestriction.getFirstNamePattern();
        String lastNamePatternRestriction = genericRestriction.getLastNamePattern();
        Date dateOlderRestriction = genericRestriction.getDateOfBirthOlder();
        Date dateYoungerRestriction = genericRestriction.getDateOfBirthYounger();

        return userMatchesBranchRestriction(user, branchRestriction)
                && userMatchesSexRestriction(user,sexRestriction)
                && userMatchesFirstNamePattern(user,firstNamePatternRestriction)
                && userMatchesLastNamePattern(user,lastNamePatternRestriction)
                && userMatchesOlderThanRestriction(user,dateOlderRestriction)
                && userMatchesYoungerThanRestriction(user,dateYoungerRestriction);
    }

    private static boolean userMatchesOlderThanRestriction(User user, Date dateOlderRestriction){
        if(dateOlderRestriction == null){
            return true;
        }
        return dateOlderRestriction.after(user.getDateOfBirth());
    }
    private static boolean userMatchesYoungerThanRestriction(User user, Date dateYoungerRestriction){
        if(dateYoungerRestriction == null){
            return true;
        }
        return dateYoungerRestriction.before(user.getDateOfBirth());
    }

    private static boolean userMatchesSexRestriction(User user, Sex sexRestriction){
        if( sexRestriction == null){
            return true;
        }
        return sexRestriction == user.getSex();
    }

    private static boolean userMatchesBranchRestriction(User user, Branch branchRestriction){
        if( branchRestriction == null){
            return true;
        }
        return branchRestriction == user.getBranch();
    }

    private static boolean userMatchesLastNamePattern(User user, String lastNamePatternRestriction){
        if(lastNamePatternRestriction == null){
            return true;
        }
        Pattern lastNamePattern = Pattern.compile(lastNamePatternRestriction, Pattern.CASE_INSENSITIVE);
        return lastNamePattern.matcher(user.getLastName()).matches();
    }

    private static boolean userMatchesFirstNamePattern(User user, String firstNamePatternRestriction){
        if(firstNamePatternRestriction == null){
            return true;
        }
        Pattern firstNamePattern = Pattern.compile(firstNamePatternRestriction, Pattern.CASE_INSENSITIVE);
        return firstNamePattern.matcher(user.getFirstName()).matches();
    }



    public static UserRestriction createGenericRestrictionFromDTO(UserRestrictionDTO restrictionDTO, Poll poll, BranchRepository branchRepository, SexRepository sexRepository) {
        UserRestriction restriction = new UserRestriction();
        restriction.setPoll(poll);
        if (restrictionDTO.branchName().isPresent()) {
            Optional<Branch> branch = branchRepository.findBranchByBranchNameEqualsIgnoreCase(restrictionDTO.branchName().get());
            if (branch.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown Branch name: " + restrictionDTO.branchName().get());
            }
            restriction.setBranchRestriction(branch.get());
        }
        if (restrictionDTO.male().isPresent()) {
            Sex sex = sexRepository.getSexBySexId(restrictionDTO.male().get());
            restriction.setSexRestrictedTo(sex);
        }
        if (restrictionDTO.firstNamePattern().isPresent()) {
            restriction.setFirstNamePattern(restrictionDTO.firstNamePattern().get());
        }
        if (restrictionDTO.lastNamePattern().isPresent()) {
            restriction.setLastNamePattern(restrictionDTO.lastNamePattern().get());
        }
        if (restrictionDTO.olderThan().isPresent()) {
            restriction.setDateOfBirthOlder(restrictionDTO.olderThan().get());
        }
        if (restrictionDTO.youngerThan().isPresent()) {
            restriction.setDateOfBirthYounger(restrictionDTO.youngerThan().get());
        }
        return restriction;
    }


    public static SpecificUserRestriction createSpecificRestrictionFromDTO(SpecificUserRestrictionDTO restrictionDTO, Poll poll, UserRepository userRepository) {
        SpecificUserRestriction restriction = new SpecificUserRestriction();
        restriction.setPoll(poll);
        restriction.setRestricted(restrictionDTO.restricted());
        restriction.setUser(UserService.getUserFromEmail(restrictionDTO.email(),userRepository));
        return restriction;
    }

}
