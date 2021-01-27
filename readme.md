

When I check my available funds
Then I should see how much funds I currently have

--------------------------------------------------
Given I have <current_amount> available funds
When I add <deposit_amount>
Then my available funds should be <total>

Examples:
| current_amount | deposit_amount |   total  |
|     100,000    |      50,000    | 150,000  |
|     200,000    |      30,000    | 230,000  |
---------------------------------------------------

Rule: To qualify for the full amount, candidates must have debt-to-income (DTI) less than 36%, credit score above 620 
and savings worth 25% of requested loan amount.

Rule: To partially qualify, candidates must still meet the same dti and credit score thresholds. 
The loan amount for partial qualified applications is four times the applicant's savings.

Given a loan applicant with <dti>, <credit_score>, and <savings>
When they apply for a loan with <requested_amount>
Then their qualification is <qualification>
And their loan amount is <loan_amount>
And their loan status is <status>

Example:
|  requested_amount  |   dti  |  credit_score  |  savings  |     qualification    |  loan_amount  |   status   |
|      250,000       |   21   |       700      | 100,000   |       qualified      |   250,000     |  qualified |
|      250,000       |   37   |       700      | 100,000   |     not qualified    |         0     |  denied    |
|      250,000       |   30   |       600      | 100,000   |     not qualified    |         0     |  denied    |
|      250,000       |   30   |       700      |  50,000   |  partially qualified |   200,000     |  qualified |

----------------------------------------------------------------------------------------------------------------

Given I have <available_funds> in available funds
When I process a qualified loan
Then the loan status is set to <status>

Example:
| loan_amount | available_funds |    status  |
|   125,000   |    100,000      |   on hold  |
|   125,000   |    200,000      |  approved  |
|   125,000   |    125,000      |  approved  |

When I process a not qualified loan
Then I should see a warning to not proceed

------------------------------------------------------------------------------------------