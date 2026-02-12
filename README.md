# 🎓 Spring Boot Thymeleaf Student Registration – Advanced Form Binding 🚀

This project extends the basic student registration form to demonstrate **advanced Thymeleaf features**:

- 🌍 **Dropdown selection** (countries)
- 💻 **Radio buttons** (favorite programming language)
- 🖥️ **Checkboxes** (favorite operating systems) – bound to a `List<String>`
- 🔁 **Iteration** with `th:each` – dynamically generate options from collections
- 🧾 **External configuration** – data loaded from `application.properties` using `@Value`
- 📦 **Collection binding** – automatically map multiple checkbox values to a `List` property

---

## 📁 Project Structure (Updated)

```
src/
├── main/
│   ├── java/
│   │   └── dev/jaqs/thymeleafdemo/
│   │       ├── ThymeleafdemoApplication.java
│   │       ├── controller/
│   │       │   └── StudentController.java            # 🎮 Injects lists from properties
│   │       └── model/
│   │           └── Student.java                      # 🧱 Added country, favoriteLanguage, favoriteSystems
│   └── resources/
│       ├── application.properties                    # ⚙️ Externalised dropdown/radio/checkbox data
│       └── templates/
│           ├── student-form.html                     # 📝 Dynamic form with th:each
│           └── student-confirmation.html             # 📄 Displays selected values, iterates over List
```

---

## 🆕 What’s New? Key Enhancements

| Feature                   | Implementation                                                                 |
|---------------------------|--------------------------------------------------------------------------------|
| **External configuration** | `@Value("${countries}")` injects comma-separated strings as `List<String>`    |
| **Dropdown (`<select>`)**  | Dynamically populated with `th:each`; selected value bound to `student.country` |
| **Radio buttons**         | Generated with `th:each`; bound to `student.favoriteLanguage` (single value)   |
| **Checkboxes**            | Generated with `th:each`; bound to `student.favoriteSystems` (`List<String>`) |
| **Collection display**    | `th:each` iterates over `student.favoriteSystems` in confirmation view        |

---

## 🧠 Deep Dive: New Concepts Explained

### 1️⃣ **Externalising Data with `application.properties`** ⚙️

```properties
countries=Brazil, France, Germany, India, Mexico, Spain, United States
languages=Java, Go, Python, Javascript, Rust, C#
operatingSystems=Microsoft Windows, MacOS, Linux, Android OS, iOS
```

- Storing configurable lists outside Java code makes the application **easier to maintain** and **localise**.
- Spring automatically converts the comma-separated string into a `List<String>` when injected with `@Value`.

```java
@Value("${countries}")
private List<String> countries;
```

✅ **No hardcoding** – you can change the options without recompiling!

---

### 2️⃣ **Injecting Collections with `@Value`** 💉

```java
@Value("${countries}")
private List<String> countries;
```

- The `${...}` syntax refers to a property key from `application.properties`.
- Spring’s conversion service automatically splits the string and creates a `List`.
- These lists are then added to the `Model` and used in the Thymeleaf view.

---

### 3️⃣ **Dynamic Dropdown with `th:each`** 🌍

```html
<select th:field="*{country}">
    <option th:each="tempCountry : ${countries}"
            th:value="${tempCountry}"
            th:text="${tempCountry}"/>
</select>
```

- `th:field="*{country}"` binds the selected value to `student.country`.
- `th:each="tempCountry : ${countries}"` iterates over the `countries` list from the model.
- For each iteration, an `<option>` is created with:
   - `th:value` – the value sent to the server.
   - `th:text` – the label displayed to the user.

✅ No need to write static `<option>` tags – the dropdown is fully dynamic.

---

### 4️⃣ **Dynamic Radio Buttons with `th:each`** 💻

```html
<input type="radio" th:field="*{favoriteLanguage}"
       th:each="tempLanguage : ${languages}"
       th:value="${tempLanguage}"
       th:text="${tempLanguage}" />
```

- `th:field="*{favoriteLanguage}"` binds the **selected** radio button value to `student.favoriteLanguage`.
- `th:each` generates one radio button per language.
- The combination of `th:field` and `th:value` ensures the correct radio is **pre-selected** when editing an existing student.

⚠️ **Important**: All radio buttons share the same `th:field` – this is what groups them together.

---

### 5️⃣ **Dynamic Checkboxes Bound to a List** 🖥️

```html
<input type="checkbox" th:field="*{favoriteSystems}"
       th:each="tempOS : ${operatingSystems}"
       th:value="${tempOS}"
       th:text="${tempOS}">
```

- `th:field="*{favoriteSystems}"` binds **all selected checkboxes** to the `favoriteSystems` property (a `List<String>`).
- Each checkbox has the same `th:field` name, so Spring MVC collects **all selected values** into a `List`.
- `th:value` defines the value sent when the checkbox is checked.
- The `th:field` automatically **marks the checkbox as checked** if the current `student.favoriteSystems` list contains that value (useful for edit forms).

✅ This is a powerful pattern for **many-to-many** relationships.

---

### 6️⃣ **Displaying Collections in the Confirmation View** 📋

```html
Favorite Operating Systems:
<ul>
    <li th:each="opSystem : ${student.favoriteSystems}"
        th:text="${opSystem}"/>
</ul>
```

- `th:each` iterates over the bound `List<String>`.
- Each selected operating system is displayed as a list item.
- If the list is empty, nothing is shown (no errors).

---

## 🔄 Data Flow Diagram

```mermaid

graph TD
    A[application.properties] -->|@Value| B(StudentController)
    B -->|model.addAttribute| C[countries]
    B -->|model.addAttribute| D[languages]
    B -->|model.addAttribute| E[operatingSystems]
    F[Student object] -->|model.addAttribute| G(student-form.html)
    C --> G
    D --> G
    E --> G
    G -->|th:each generates| H[Dynamic dropdown / radios / checkboxes]
    H -->|User submits POST| I[/processStudentForm]
    I -->|@ModelAttribute binds| J[Student object populated]
    J -->|auto-added to model| K[student-confirmation.html]
    K -->|th:each displays| L[List of favorite systems]

```

---

## 🧪 Run the Application

1. Ensure all dependencies (Spring Boot, Thymeleaf) are in `pom.xml`.
2. Start the app:
   ```bash
   ./mvnw spring-boot:run
   ```
3. Visit: [http://localhost:8080/showStudentForm](http://localhost:8080/showStudentForm)
4. Fill out the form, select multiple operating systems, and submit.
5. See the confirmation page with all selected values.

---

## 📚 Concepts Learned in This Module

✅ **External configuration** – Inject lists from `.properties` files using `@Value`.  
✅ **Dynamic form generation** – Use `th:each` to create dropdowns, radios, and checkboxes.  
✅ **Collection binding** – Bind multiple checkbox values to a `List` property.  
✅ **Thymeleaf field binding** – `th:field` works with single and multi-valued fields.  
✅ **Model attribute propagation** – `@ModelAttribute` automatically adds the bound object to the model.  
✅ **Iteration in views** – Display collections with `th:each`.

---

## 📊 Comparison: Static vs Dynamic Form Generation

| Aspect               | Static (hardcoded)                          | Dynamic (th:each + properties)              |
|----------------------|---------------------------------------------|---------------------------------------------|
| **Maintenance**      | Change Java code or HTML for every update   | Update `application.properties` only        |
| **Flexibility**      | Fixed at compile-time                      | Runtime – no rebuild required              |
| **Localisation**     | Requires separate HTML/Java changes        | Can be externalised per locale             |
| **Code length**      | Verbose, repetitive                        | Concise, DRY                               |

---

## 🔜 Next Steps

- Add **validation** (`@NotNull`, `@Size`) to the `Student` model and display error messages.
- Use **`@ModelAttribute` on a method** to populate reference data once for all requests.
- Internationalise (i18n) the country/language lists using `MessageSource`.
- Persist the student data in a database with Spring Data JPA.

---

## 📖 Resources

- [Thymeleaf + Spring: Select Boxes](https://www.thymeleaf.org/doc/tutorials/3.1/thymeleafspring.html#select-boxes)
- [Thymeleaf + Spring: Checkboxes](https://www.thymeleaf.org/doc/tutorials/3.1/thymeleafspring.html#checkbox-fields)
- [Spring @Value – List Injection](https://www.baeldung.com/spring-value-annotation#list-values)
- [Spring MVC Form Handling with Collections](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-modelattrib-method-arg.html#page-title)

---

**Happy Coding!** 💻✨  
This demo is a solid foundation for building dynamic, data-driven web applications with Spring Boot and Thymeleaf.