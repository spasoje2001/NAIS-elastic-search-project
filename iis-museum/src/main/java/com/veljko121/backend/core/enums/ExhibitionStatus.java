package com.veljko121.backend.core.enums;

public enum ExhibitionStatus {
    PROPOSED,          // Exhibition dates and room proposed by the organizer.
    CURATING,          // Curator determining the theme, selecting items, and detailing creative aspects.
    AWAITING_APPROVAL, // Exhibition plan is complete and awaiting approval from museum administration.
    READY_TO_OPEN,     // Exhibition is fully installed and ready for visitors.
    OPEN,              // Exhibition is currently open to the public.
    CLOSED,            // Exhibition is no longer open to the public.
    ARCHIVED           // Exhibition is kept on record for historical/reference purposes.
}
