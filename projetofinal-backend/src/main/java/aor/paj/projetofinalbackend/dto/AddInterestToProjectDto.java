package aor.paj.projetofinalbackend.dto;

import jakarta.xml.bind.annotation.XmlElement;

public class AddInterestToProjectDto {
    private InterestDto interest;

    @XmlElement
    public InterestDto getInterest() {
        return interest;
    }

    public void setInterest(InterestDto interest) {
        this.interest = interest;
    }
}
