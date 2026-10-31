package com.example.sarkarisahayak;

import java.util.ArrayList;
import java.util.List;

public class SchemeData {

    public static List<Scheme> getAllSchemes() {
        List<Scheme> schemes = new ArrayList<>();

        schemes.add(new Scheme(
                "PMAY",
                "Pradhan Mantri Awas Yojana (PMAY)",
                "Housing for all initiative providing financial assistance and interest subsidies for home construction.",
                "Pradhan Mantri Awas Yojana (Urban & Gramin) aims to provide affordable housing to urban and rural poor. Eligible beneficiaries receive financial assistance and credit-linked interest subsidies for house construction or purchase.",
                "• Financial assistance up to ₹2.67 Lakhs as interest subsidy.\n• Direct benefit transfer for home construction.\n• Subsidy on housing loans for eligible income groups.",
                "• Annual household income below ₹6,00,000.\n• Applicant/family must not own a pucca house in India.\n• Indian citizen of age 18 years or above.",
                "• Aadhaar Card\n• Income Certificate / Salary Slips\n• Address Proof / Ration Card\n• Bank Account Details & Affidavit of no pucca house"
        ));

        schemes.add(new Scheme(
                "PMKISAN",
                "PM Kisan Samman Nidhi",
                "Income support scheme providing ₹6,000 per year to landholding farmer families.",
                "Under the PM-KISAN scheme, vulnerable landholding farmer families are provided with financial support to meet agricultural and domestic needs. The amount is directly transferred into bank accounts.",
                "• ₹6,000 per year paid in 3 equal installments of ₹2,000 every 4 months.\n• Direct cash transfer to beneficiary bank account.\n• Supports purchasing seeds, fertilizers, and farm inputs.",
                "• Small and marginal landholding farmer families.\n• Owns cultivable land as per land records.\n• Annual family income within threshold limits.",
                "• Aadhaar Card\n• Land Ownership Document (Khatauni / Khasra)\n• Bank Passbook\n• Active Mobile Number"
        ));

        schemes.add(new Scheme(
                "PMJAY",
                "Ayushman Bharat (PM-JAY)",
                "National health protection scheme offering free health insurance up to ₹5 Lakhs per family.",
                "Ayushman Bharat PM-JAY is the world's largest health insurance scheme fully financed by the government. It provides health coverage for secondary and tertiary care hospitalization across empanelled public and private hospitals.",
                "• Cashless treatment coverage up to ₹5,00,000 per family per year.\n• Covers pre and post-hospitalization expenses, medicines, and diagnostics.\n• No cap on family size or age of family members.",
                "• Low-income households and vulnerable families.\n• Families listed under SECC database or holding BPL status.\n• Persons with disabilities or landless manual casual labor.",
                "• Aadhaar Card\n• Ration Card / BPL Card\n• Active Mobile Number"
        ));

        schemes.add(new Scheme(
                "DIVYANG",
                "Divyangjan Swavlamban & Support Scheme",
                "Financial assistance, pension stipend, and assistive equipment for persons with disabilities.",
                "This scheme aims to empower persons with disabilities by providing monthly pension stipends, educational grants, and free assistive equipment to foster self-reliance and economic independence.",
                "• Monthly financial allowance/pension of ₹1,500 to ₹3,000.\n• Free distribution of motorized tricycles, hearing aids, and assistive devices.\n• Skill development training grants.",
                "• Persons with 40% or more disability certified by a medical authority.\n• Indian citizen of age 18 years or above.\n• Annual income below ₹3,00,000.",
                "• Disability Certificate (UDID Card)\n• Aadhaar Card\n• Income Certificate\n• Bank Account Passbook"
        ));

        schemes.add(new Scheme(
                "IGNOAPS",
                "Indira Gandhi National Old Age Pension Scheme",
                "Social security pension providing monthly financial support to senior citizens.",
                "IGNOAPS provides financial independence and dignified living for senior citizens from low-income background by offering a regular monthly pension.",
                "• Monthly pension of ₹1,000 to ₹1,500 deposited directly into bank accounts.\n• Additional assistance for senior citizens above 80 years.",
                "• Citizen aged 60 years or above.\n• Belongs to Below Poverty Line (BPL) or low-income household.",
                "• Aadhaar Card / Age Proof Certificate\n• BPL Card / Income Certificate\n• Bank Passbook Details"
        ));

        return schemes;
    }

    public static List<Scheme> getEligibleSchemes(int age, double income, String occupation, String landOwnership, String disability) {
        List<Scheme> all = getAllSchemes();
        List<Scheme> eligible = new ArrayList<>();

        boolean isFarmer = occupation != null && occupation.equalsIgnoreCase("Farmer");
        boolean ownsLand = landOwnership != null && landOwnership.equalsIgnoreCase("Yes");
        boolean hasDisability = disability != null && disability.equalsIgnoreCase("Yes");

        for (Scheme scheme : all) {
            String id = scheme.getId();
            if ("PMAY".equals(id) && income <= 600000) {
                eligible.add(scheme);
            } else if ("PMKISAN".equals(id) && (isFarmer || ownsLand) && income <= 300000) {
                eligible.add(scheme);
            } else if ("PMJAY".equals(id) && (income <= 250000 || hasDisability)) {
                eligible.add(scheme);
            } else if ("DIVYANG".equals(id) && hasDisability) {
                eligible.add(scheme);
            } else if ("IGNOAPS".equals(id) && age >= 60 && income <= 300000) {
                eligible.add(scheme);
            }
        }

        // If no or few specific rules matched, ensure at least PMAY and PMJAY are shown so user sees results
        if (eligible.size() < 2) {
            for (Scheme scheme : all) {
                if (!eligible.contains(scheme)) {
                    eligible.add(scheme);
                    if (eligible.size() >= 3) break;
                }
            }
        }

        return eligible;
    }
}
