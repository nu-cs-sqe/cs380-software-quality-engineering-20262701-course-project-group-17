# Week 3 (10/12/2026-10/18/2026)
**Planning and Progress Tracking**:
1. [done] All: Form team, choose the Chess (Basic) option, accept the GitHub Classroom repository
2. [done] Kris: Initial repository setup and README

# Week 4 (10/19/2026-10/25/2026)
## Part 1. Planning
**Planning date**: 11/20/2026 (we started the Week 4 items late and are catching up now)

**Attendees**: Kris, Max, Cindy

**Decisions**
- We're building the GUI in Java Swing. The instructor's Swing example is our reference, but we're writing our own version rather than copying it.
- This week's feature is the Game Setup Phase. The user story, acceptance criteria, and use case are in [requirements/game-setup.md](requirements/game-setup.md), and the design is in [design/README.md](design/README.md).
- We're turning the quality gates on from the first day: CI on every push and pull request, Checkstyle based on *Clean Code*, and 100% branch coverage and mutation score for the `domain` package.
- Each class goes through the same steps: write the BVA, open a draft PR, do TDD with red and green commits, mark the PR ready for review, get one approval, and merge.

**Plan**
1. Max: GitHub Actions workflow and build badge
2. Kris: Checkstyle, JaCoCo, and PIT quality gates, plus the team coding standard ([PR #5](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/5))
3. Kris: Merge the two CI workflows into one that also runs PIT ([PR #7](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/7))
4. Cindy: User story, acceptance criteria, and use case for Game Setup ([PR #6](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/6))
5. Kris: Design doc and chess rules summary; create the Project Board items (PR: _link_)
6. All: Turn on branch protection for `main` (one approval and passing CI) and meet with the instructor
7. Kris: `Position`: BVA + TDD (PR: _link_)
8. Kris: `Piece`, `PieceType`, `PieceColor`: BVA + TDD (PR: _link_)
9. Max: `Board` + `Board.createStandard()`: BVA + TDD (PR: _link_)
10. Cindy: `Player` + `Game`: BVA + TDD (PR: _link_)
11. Cindy: Swing UI (Welcome screen with name validation, board view, game stats view) wired to `Game` (PR: _link_)
12. All: Review each other's PRs within 24 hours

## Part 2. Progress Tracking
**Evaluation date**: 11/22/2026

1. [done] Max: GitHub Actions workflow and build badge (committed directly to `main` before branch protection was set up)
2. [done] Kris: Quality gates and coding standard ([PR #5](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/5))
3. [done] Kris: Single CI workflow with PIT ([PR #7](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/7))
4. [done] Cindy: Game Setup requirements ([PR #6](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/6))
5. [done] Kris: Design doc and chess rules summary ([PR #8](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/8))
6. [in progress] All: Branch protection, project board, and instructor meeting
7. [in review] Kris: `Position`, BVA ([PR #9](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/9)) and TDD (PR: _link_)
8. [in review] Kris: `Piece`, BVA ([PR #10](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/10)) and TDD (PR: _link_)
9. [done] Max: `Board` and `Board.createStandard()`, BVA ([PR #11](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/11)) and TDD ([PR #13](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/13)). Max added placeholder `Position` and `Piece` classes with the agreed signatures so `Board` didn't have to wait for Kris's branches.
10. [in progress] Cindy: `Player` BVA done ([PR #12](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/12)); `Player` TDD and `Game` not started
11. [not started] Cindy: Swing UI

**Retrospective**: We started the Week 4 work late, so most of it happened in the last few days. The quality gates paid off early. CI ran Checkstyle, 100% coverage, and PIT on every PR, and reviewers caught real gaps in the BVA files, like a missing self-reference case for `equals` and a test where row and col were equal and couldn't catch a swap. Max's placeholder classes worked well, because they let `Board` go through TDD without waiting on `Position` and `Piece`. Next week we want to start each class as soon as the plan is set instead of near the deadline, review PRs within a day, and get `Player`, `Game`, and the UI moving earlier, since they sit at the end of the dependency chain.

## Part 3. Individual Progress
### Kris
- PRs created: [#5](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/5), [#7](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/7), [#8](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/8), [#9](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/9), [#10](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/10), `Position` TDD (_link_), `Piece` TDD (_link_)
- PRs merged: [#5](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/5), [#7](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/7), [#8](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/8), [#9](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/9), [#10](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/10)
- PRs reviewed: _links_

### Max
- PRs created: [#11](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/11), [#13](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/13)
- PRs merged: [#11](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/11), [#13](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/13)
- PRs reviewed: _links_

### Cindy
- PRs created: [#6](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/6), [#12](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/12)
- PRs merged: [#6](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/6), [#12](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/pull/12)
- PRs reviewed: _links_

# Week 5 (10/26/2026-11/01/2026)
**Planning and Progress Tracking**:
1. [not started] Person: Task (Links to PR)

# One section per week until the last week of class (the week before the Finals week)
