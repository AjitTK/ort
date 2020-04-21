/*
 * Copyright (C) 2020 Bosch.IO GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 * License-Filename: LICENSE
 */

package org.ossreviewtoolkit.detekt

import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import io.gitlab.arturbosch.detekt.api.internal.absolutePath

//import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtImportList
import org.jetbrains.kotlin.psi.psiUtil.siblings

class OrtImportOrder : Rule() {
    override val issue = Issue(
        javaClass.simpleName,
        Severity.Style,
        "Reports files that do not follow ORT's order for imports",
        Debt.FIVE_MINS
    )

    @ExperimentalStdlibApi
    override fun visitImportList(importList: KtImportList) {
        super.visitImportList(importList)

        //val ktElement = importList.siblings(withItself = false).filterIsInstance<KtElement>().firstOrNull() ?: return


        val importPaths = importList.imports.mapTo(mutableListOf()) { it.importPath.toString() }
        val sortedImportPaths = importPaths.sorted().toMutableList()
        if (importPaths != sortedImportPaths) {
            var skippedCommonPrefix = false

            while (importPaths.size > 0 && importPaths.first() == sortedImportPaths.first()) {
                importPaths.removeFirst()
                sortedImportPaths.removeFirst()
                skippedCommonPrefix = true
            }

            if (skippedCommonPrefix) {
                importPaths.add(0, "...")
                sortedImportPaths.add(0, "...")
            }

            var skippedCommonSuffix = false

            while (importPaths.size > 0 && importPaths.last() == sortedImportPaths.last()) {
                importPaths.removeLast()
                sortedImportPaths.removeLast()
                skippedCommonSuffix = true
            }

            if (skippedCommonSuffix) {
                importPaths += "..."
                sortedImportPaths += "..."
            }

            println("Invalid import order in file '${importList.containingKtFile.absolutePath()}'.")
            println("Actual:\n\t${importPaths.joinToString("\n\t")}")
            println("Expected:\n\t${sortedImportPaths.joinToString("\n\t")}")
            println(importList.children.joinToString())
        }
    }
}
